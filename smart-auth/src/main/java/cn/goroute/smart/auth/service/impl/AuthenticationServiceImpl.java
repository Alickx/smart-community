package cn.goroute.smart.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.auth.service.AuthenticationService;
import cn.goroute.smart.common.api.ResultCode;
import cn.goroute.smart.common.constant.RedisKeyConstant;
import cn.goroute.smart.common.dao.MemberDao;
import cn.goroute.smart.common.entity.dto.MemberDTO;
import cn.goroute.smart.common.entity.pojo.Member;
import cn.goroute.smart.common.entity.vo.MemberLoginVO;
import cn.goroute.smart.common.entity.vo.MemberRegisterVO;
import cn.goroute.smart.common.utils.*;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: Alickx
 * @Date: 2022/03/31/21:05
 * @Description: 用户认证服务实现类
 */
@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    MemberDao memberDao;

    @Autowired
    RedisUtil redisUtil;

    /**
     * 禁止注册时间
     */
    private static final int BAN_TIME = 10 * 60;

    private static final int ERROR_COUNT = 5;

    /**
     * 登录
     * @param memberLoginVO 登录信息
     * @param request 请求
     * @return 登录结果
     */
    @Override
    public Result login(MemberLoginVO memberLoginVO, HttpServletRequest request) {

        Member member = memberDao.selectOne(new LambdaQueryWrapper<Member>()
                .eq(Member::getEmail, memberLoginVO.getUsername()));

        if (member == null) {
            return Result.error("用户不存在");
        }

        if (!DigestUtil.bcryptCheck(memberLoginVO.getPassWord(), member.getPassWord())) {
            return Result.error("用户名或密码错误");
        }

        // 获取request中的ip，设备信息
        String clientIp = ServletUtil.getClientIP(request);
        String os = GetLoginUserAgentUtil.getOs(request);
        String browser = GetLoginUserAgentUtil.getBrowser(request);
        LocalDateTime now = LocalDateTimeUtil.now();
        member.setOs(os);
        member.setBrowser(browser);
        member.setLastLoginIp(clientIp);
        member.setLastLoginTime(now);

        memberDao.updateById(member);

        // 登录成功，生成token
        StpUtil.login(member.getUid());

        MemberDTO memberDTO = new MemberDTO();
        BeanUtils.copyProperties(member, memberDTO);

        Map<String, Object> map = new HashMap<>(4);
        map.put("access_token",StpUtil.getTokenValue());
        map.put("user_info",memberDTO);
        map.put("permission_list",StpUtil.getPermissionList());
        map.put("role_list",StpUtil.getRoleList());

        return Result.ok().put("data",map);
    }

    /**
     * 注册
     * @param memberRegisterVO 注册信息
     * @return 注册结果
     */
    @Override
    public Result register(MemberRegisterVO memberRegisterVO) {

        String memberRegEmail = memberRegisterVO.getUsername();

        String regBanKey = RedisKeyConstant.REG_SEND_BAN_KEY + memberRegEmail;
        //如果禁止注册则没必要去查询了
        if (redisUtil.hasKey(regBanKey)) {
            return Result.error("错误次数过多，已被禁止注册10分钟");
        }

        //校验是否存在该用户
        Member memberIsExist = memberDao.selectOne(new LambdaQueryWrapper<Member>()
                .eq(Member::getEmail, memberRegEmail));

        if (memberIsExist != null) {
            return Result.error("该邮箱已经注册，请换一个吧");
        }

        String regKey = RedisKeyConstant.REG_CAPTCHA_KEY + memberRegEmail;

        //校验验证码是否过期或存在
        if (!redisUtil.hasKey(regKey)) {
            return Result.error(ResultCode.FAILED.getCode(),ResultCode.FAILED.getMessage());
        }

        int errorCount = (int) redisUtil.hget(regKey, "errorCount");

        if (errorCount >= ERROR_COUNT) {
            //当错误次数超过5次后，对该邮箱禁止注册10分钟
            String banKey = RedisKeyConstant.REG_SEND_BAN_KEY + memberRegEmail;

            redisUtil.set(banKey, "", BAN_TIME);
            redisUtil.del(regKey);

            return Result.error("验证码输入次数过多，该邮箱已被禁止注册10分钟");
        }
        String memberRegCaptcha = (String) redisUtil.hget(regKey, "captcha");

        //错误则直接返回,且错误次数增加
        if (!Objects.equals(memberRegCaptcha, memberRegisterVO.getCaptcha())) {
            log.info("memberRegCaptcha={},memberRegVo={}", memberRegCaptcha, memberRegisterVO.getCaptcha());
            if (redisUtil.hHasKey(regKey, "captcha")) {
                redisUtil.hincr(regKey, "errorCount", 1);
            }

            return Result.error("验证码错误，请重新输入！");
        }

        //正确则执行注册流程
        Member member = new Member();
        String memberRegVoPassWord = memberRegisterVO.getPassWord();
        String bcryptPassWord = DigestUtil.bcrypt(memberRegVoPassWord);

        //存储用户数据
        member.setPassWord(bcryptPassWord);
        member.setEmail(memberRegEmail);
        int registerResult = memberDao.insert(member);
        if (registerResult != 1) {
            log.error("用户{}注册失败，注册验证码为{}", memberRegEmail, memberRegCaptcha);
        }
        String regCountKey = RedisKeyConstant.REG_SEND_COUNT_KEY + memberRegEmail;

        redisUtil.del(regKey, regCountKey);

        return Result.ok("用户注册成功");

    }
}
