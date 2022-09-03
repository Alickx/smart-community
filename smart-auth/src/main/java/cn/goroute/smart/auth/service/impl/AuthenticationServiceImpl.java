package cn.goroute.smart.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.auth.constant.AuthConstant;
import cn.goroute.smart.auth.entity.dto.AuthLoginSuccessDto;
import cn.goroute.smart.auth.entity.vo.MemberLoginVo;
import cn.goroute.smart.auth.entity.vo.MemberRegisterVo;
import cn.goroute.smart.auth.service.AuthenticationService;
import cn.goroute.smart.common.constant.RedisKeyConstant;
import cn.goroute.smart.common.entity.bo.MemberBo;
import cn.goroute.smart.common.entity.dto.MemberDto;
import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.common.feign.MemberFeignService;
import cn.goroute.smart.common.service.AuthService;
import cn.goroute.smart.common.utils.GetLoginUserAgentUtil;
import cn.goroute.smart.common.utils.ModelConverterUtils;
import cn.goroute.smart.redis.util.RedisUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Author: Alickx
 * @Date: 2022/03/31/21:05
 * @Description: 用户认证服务实现类
 */
@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Resource
    MemberFeignService memberFeignService;

    @Resource
    RedisUtil redisUtil;

    @Resource
    AuthService authService;

    /**
     * 登录
     *
     * @param memberLoginVo 登录信息
     * @param request       请求
     * @return 登录结果
     */
    @Override
    @Transactional
    public Response login(MemberLoginVo memberLoginVo, HttpServletRequest request) {

        MemberBo member = memberFeignService.getMemberByEmail(memberLoginVo.getUsername());

        if (member == null) {
            return Response.failure("用户不存在");
        }

        if (!DigestUtil.bcryptCheck(memberLoginVo.getPassWord(), member.getPassWord())) {
            return Response.failure("用户名或密码错误");
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

        memberFeignService.updateMember(member);

        // 登录成功，生成token
        StpUtil.login(member.getId());

        MemberDto memberDTO = new MemberDto();
        ModelConverterUtils.copy(member, memberDTO);

        AuthLoginSuccessDto authLoginSuccessDto = new AuthLoginSuccessDto();
        authLoginSuccessDto.setAccessToken(StpUtil.getTokenValue())
                .setUserInfo(memberDTO)
                .setPermissions(StpUtil.getPermissionList())
                .setRoles(authService.getRoleList(member.getId()));
        return Response.success(authLoginSuccessDto);
    }

    /**
     * 注册
     *
     * @param memberRegisterVO 注册信息
     * @return 注册结果
     */
    @Override
    @Transactional
    public Response register(MemberRegisterVo memberRegisterVO) {

        String memberRegEmail = memberRegisterVO.getUsername();

        String regBanKey = RedisKeyConstant.REG_SEND_BAN_KEY + memberRegEmail;
        //如果禁止注册则没必要去查询了
        if (redisUtil.hasKey(regBanKey)) {
            return Response.failure("错误次数过多，已被禁止注册10分钟");
        }

        //校验是否存在该用户
        Boolean isRegResult = memberFeignService.isEmailRegistered(memberRegEmail);

        if (Boolean.TRUE.equals(isRegResult)) {
            return Response.failure("该邮箱已经注册，请换一个吧");
        }

        String regKey = RedisKeyConstant.REG_CAPTCHA_KEY + memberRegEmail;

        //校验验证码是否过期或存在
        if (!redisUtil.hasKey(regKey)) {
            return Response.failure();
        }

        int errorCount = (int) redisUtil.hget(regKey, "errorCount");

        if (errorCount >= AuthConstant.Register.REGISTER_ERROR_COUNT) {
            //当错误次数超过5次后，对该邮箱禁止注册10分钟
            String banKey = RedisKeyConstant.REG_SEND_BAN_KEY + memberRegEmail;

            redisUtil.set(banKey, "", AuthConstant.Register.REGISTER_BAN_TIME);
            redisUtil.del(regKey);

            return Response.failure("验证码输入次数过多，该邮箱已被禁止注册10分钟");
        }
        String memberRegCaptcha = (String) redisUtil.hget(regKey, "captcha");

        //错误则直接返回,且错误次数增加
        if (!Objects.equals(memberRegCaptcha, memberRegisterVO.getCaptcha())) {
            log.info("memberRegCaptcha={},memberRegVo={}", memberRegCaptcha, memberRegisterVO.getCaptcha());
            if (redisUtil.hHasKey(regKey, "captcha")) {
                redisUtil.hincr(regKey, "errorCount", 1);
            }

            return Response.failure("验证码错误，请重新输入！");
        }

        //正确则执行注册流程
        MemberBo member = new MemberBo();
        String memberRegVoPassWord = memberRegisterVO.getPassWord();
        String bcryptPassWord = DigestUtil.bcrypt(memberRegVoPassWord);

        //存储用户数据
        member.setPassWord(bcryptPassWord);
        member.setEmail(memberRegEmail);
        Boolean saveResult = memberFeignService.saveMember(member);
        if (Boolean.FALSE.equals(saveResult)) {
            log.error("用户{}注册失败，注册验证码为{}", memberRegEmail, memberRegCaptcha);
            return Response.error("注册失败，请稍后再试");
        }
        String regCountKey = RedisKeyConstant.REG_SEND_COUNT_KEY + memberRegEmail;

        redisUtil.del(regKey, regCountKey);

        return Response.success();

    }
}
