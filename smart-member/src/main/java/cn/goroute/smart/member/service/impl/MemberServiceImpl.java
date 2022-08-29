package cn.goroute.smart.member.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.api.ResultCode;
import cn.goroute.smart.common.constant.RedisKeyConstant;
import cn.goroute.smart.member.entity.vo.MemberLoginVo;
import cn.goroute.smart.member.entity.vo.MemberRegisterVo;
import cn.goroute.smart.common.utils.GetLoginUserAgentUtil;
import cn.goroute.smart.common.utils.RedisUtil;
import cn.goroute.smart.member.constant.MemberConstant;
import cn.goroute.smart.member.mapper.MemberMapper;
import cn.goroute.smart.member.entity.dto.MemberDto;
import cn.goroute.smart.member.entity.pojo.Member;
import cn.goroute.smart.member.entity.vo.MemberInfoUpdateVo;
import cn.goroute.smart.common.exception.ServiceException;
import cn.goroute.smart.common.service.AuthService;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.member.service.MemberService;
import cn.goroute.smart.member.util.IllegalTextCheckUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Service("memberService")
@Slf4j
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    IllegalTextCheckUtil illegalTextCheckUtil;

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    MemberBanMapper memberBanMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    AuthService authService;

    /**
     * 更新用户展示信息
     *
     * @param memberVO 用户展示信息VO
     * @return 更新结果
     */
    @Override
    public Result updateMemberInfo(MemberInfoUpdateVo memberVO) {

        //检查用户名是否存在违禁词
        Boolean nickNameCheckResult = illegalTextCheckUtil.checkText(memberVO.getNickName());
        Boolean introCheckResult = illegalTextCheckUtil.checkText(memberVO.getIntro());
        if (nickNameCheckResult || introCheckResult) {
            return Result.error("用户名或简介包含违禁词");
        }
        Member member = new Member();
        long loginId = authService.getLoginUid();
        BeanUtils.copyProperties(memberVO, member);
        member.setUid(loginId);
        int result = memberMapper.updateById(member);
        MemberDto dto = new MemberDto(member);
        BeanUtils.copyProperties(member, dto);
        if (result > 0) {
            return Result.ok("用户信息更新成功！").put("data", dto);
        } else {
            log.error("用户信息更新失败！用户信息=>{}", memberVO);
            throw new ServiceException("用户信息更新失败");
        }
    }

    /**
     * 用户登陆
     *
     * @param memberLoginVO 用户登陆信息
     * @param request       请求
     * @return 登陆结果
     */
    @Override
    public Result login(MemberLoginVo memberLoginVO, HttpServletRequest request) {

        Member member = memberMapper.selectOne(new LambdaQueryWrapper<Member>()
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

        memberMapper.updateById(member);

        // 登录成功，生成token
        StpUtil.login(member.getUid());

        MemberDto memberDTO = new MemberDto(member);
        BeanUtils.copyProperties(member, memberDTO);

        Map<String, Object> map = new HashMap<>(4);
        map.put("access_token", StpUtil.getTokenValue());
        map.put("user_info", memberDTO);
        map.put("permission_list", StpUtil.getPermissionList());
        map.put("role_list", authService.getRoleList(member.getUid()));
        return Result.ok().put("data", map);
    }

    /**
     * 用户注册
     *
     * @param memberRegisterVO 用户注册信息
     * @return 注册结果
     */
    @Override
    public Result register(MemberRegisterVo memberRegisterVO) {

        String memberRegEmail = memberRegisterVO.getUsername();

        String regBanKey = RedisKeyConstant.REG_SEND_BAN_KEY + memberRegEmail;
        //如果禁止注册则没必要去查询了
        if (redisUtil.hasKey(regBanKey)) {
            return Result.error("错误次数过多，已被禁止注册10分钟");
        }

        //校验是否存在该用户
        Member memberIsExist = memberMapper.selectOne(new LambdaQueryWrapper<Member>()
                .eq(Member::getEmail, memberRegEmail));

        if (memberIsExist != null) {
            return Result.error("该邮箱已经注册，请换一个吧");
        }

        String regKey = RedisKeyConstant.REG_CAPTCHA_KEY + memberRegEmail;

        //校验验证码是否过期或存在
        if (!redisUtil.hasKey(regKey)) {
            return Result.error(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage());
        }

        int errorCount = (int) redisUtil.hget(regKey, "errorCount");

        if (errorCount >= MemberConstant.Register.REGISTER_ERROR_COUNT) {
            //当错误次数超过指定次数后，对该邮箱禁止注册10分钟
            String banKey = RedisKeyConstant.REG_SEND_BAN_KEY + memberRegEmail;

            redisUtil.set(banKey, "", MemberConstant.Register.REGISTER_BAN_TIME);
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
        int registerResult = memberMapper.insert(member);
        if (registerResult != 1) {
            log.error("用户{}注册失败，注册验证码为{}", memberRegEmail, memberRegCaptcha);
        }
        String regCountKey = RedisKeyConstant.REG_SEND_COUNT_KEY + memberRegEmail;

        redisUtil.del(regKey, regCountKey);

        return Result.ok("用户注册成功");
    }
}