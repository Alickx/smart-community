package cn.goroute.smart.auth.module.login.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.auth.domain.entity.AuthUserEntity;
import cn.goroute.smart.auth.domain.form.UserLoginForm;
import cn.goroute.smart.auth.domain.form.UserRegisterForm;
import cn.goroute.smart.auth.domain.vo.CustomUserDetailsVO;
import cn.goroute.smart.auth.feign.FeignUserProfileService;
import cn.goroute.smart.auth.module.captcha.CaptchaValidateResult;
import cn.goroute.smart.auth.module.captcha.CaptchaValidator;
import cn.goroute.smart.auth.module.login.converter.AuthUserConverter;
import cn.goroute.smart.auth.module.login.manager.AuthUserManagerService;
import cn.goroute.smart.auth.module.login.mapper.AuthUserMapper;
import cn.goroute.smart.auth.module.login.mq.RegisterMessageTemplate;
import cn.goroute.smart.auth.module.login.service.AuthUserService;
import cn.goroute.smart.auth.module.login.util.JwtUtil;
import cn.goroute.smart.common.constant.enums.ErrorCodeEnum;
import cn.goroute.smart.common.exception.BusinessException;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.user.domain.vo.UserProfileVO;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * @author caiguopeng
 * @description 针对表【auth_user(用户授权表)】的数据库操作Service实现
 * @createDate 2022-09-17 19:21:38
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class AuthUserServiceImpl extends ServiceImpl<AuthUserMapper, AuthUserEntity>
        implements AuthUserService {

    private final AuthUserMapper authUserMapper;
    private final FeignUserProfileService feignUserProfileService;
    private final CaptchaValidator captchaValidator;
    private final AuthUserManagerService authUserManagerService;
    private final RegisterMessageTemplate registerMessageTemplate;
    private final JwtUtil jwtUtil;


    /**
     * 用户登录
     *
     * @param userLoginForm 用户登录信息
     * @return 用户信息
     */
    @Override
    public R<CustomUserDetailsVO> login(UserLoginForm userLoginForm) {

        log.info("用户登录，登录信息：{}", userLoginForm);

        AuthUserEntity authUserEntity;

        // 判断是用户名登录还是邮箱登录

        String pattern = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

        boolean match = ReUtil.isMatch(pattern, userLoginForm.getUserName());

        if (match) {
            authUserEntity = authUserMapper.selectByUserEmail(userLoginForm.getUserName());
        } else {
            authUserEntity = authUserMapper.selectByUserName(userLoginForm.getUserName());
        }

        if (authUserEntity == null) {
            return R.failed(ErrorCodeEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        if (!BCrypt.checkpw(userLoginForm.getPassword(), authUserEntity.getPassword())) {
            return R.failed(ErrorCodeEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        Long userId = authUserEntity.getId();

        R<UserProfileVO> userProfileResult = feignUserProfileService.getUserProfile(userId);
        if (userProfileResult.getData() == null) {
            throw new BusinessException(ErrorCodeEnum.USER_COMMON_ERROR, "用户信息不存在");
        }


        StpUtil.login(userId);
        String tokenValue = StpUtil.getTokenValue();

        CustomUserDetailsVO customUserDetailsVO = new CustomUserDetailsVO();
        customUserDetailsVO
                .setAccessToken(tokenValue)
                .setUserProfile(userProfileResult.getData());

        return R.ok(customUserDetailsVO);

    }

    /**
     * 用户注册
     *
     * @param userRegisterForm 用户注册信息
     * @return 用户信息
     */
    @Override
    public R<Boolean> register(UserRegisterForm userRegisterForm) {

        CaptchaValidateResult validate = captchaValidator.validate(userRegisterForm.getCaptchaId());

        if (!validate.isSuccess()) {
            return R.failed(ErrorCodeEnum.USER_COMMON_ERROR, "验证码错误");
        }

        AuthUserEntity authUserEntity =
                authUserMapper.selectByUserName(userRegisterForm.getUserName());

        if (null != authUserEntity) {
            return R.failed(ErrorCodeEnum.USER_COMMON_ERROR, "用户名已存在");
        }

        AuthUserEntity authUserEntity1 = authUserMapper.selectByUserEmail(userRegisterForm.getUserEmail());

        if (null != authUserEntity1) {
            return R.failed(ErrorCodeEnum.USER_COMMON_ERROR, "用户邮箱已存在");
        }

        AuthUserEntity entity = AuthUserConverter.INSTANCE.formToEntity(userRegisterForm);

        authUserManagerService.saveAuthUser(entity);

        // 发送激活邮件
        registerMessageTemplate.sendAsyncRegisterMessage(entity.getUserName(),entity.getUserEmail());

        return R.ok(true);

    }


    /**
     * 登出
     *
     * @return 登出结果
     */
    @Override
    public R<Boolean> logout() {
        StpUtil.logout();
        return R.ok();
    }

    @Override
    public R<Boolean> checkName(String username) {

        AuthUserEntity authUserEntity = authUserMapper.selectByUserName(username);

        if (null != authUserEntity) {
            return R.ok(false);
        }

        return R.ok(true);

    }

    @Override
    public R<Boolean> checkEmail(String email) {

        AuthUserEntity authUserEntity = authUserMapper.selectByUserEmail(email);

        if (null != authUserEntity) {
            return R.ok(false);
        }

        return R.ok(true);

    }

    @Override
    public R<Boolean> activate(String token) {

		if (!jwtUtil.validateToken(token)) {
			return R.failed(ErrorCodeEnum.PARAM_ERROR, "token错误");
		}

        String userEmail;

        try {
            userEmail = jwtUtil.get(token, "userEmail");
        } catch (Exception e) {
            return R.failed(ErrorCodeEnum.PARAM_ERROR, "token错误");
        }

        if (StrUtil.isBlank(userEmail)) {
            return R.failed(ErrorCodeEnum.PARAM_ERROR, "token错误");
        }

        AuthUserEntity authUserEntity = authUserMapper.selectByUserEmail(userEmail);

        if (null == authUserEntity) {
            return R.failed(ErrorCodeEnum.PARAM_ERROR, "用户不存在");
        }

        // 对比账号名和邮箱
        String usernameFromToken = jwtUtil.getUsernameFromToken(token);

        if (!authUserEntity.getUserName().equals(usernameFromToken)) {
            return R.failed(ErrorCodeEnum.PARAM_ERROR, "token错误");
        }

        // 激活账号
		authUserEntity.setIsActivate(1);
		authUserManagerService.updateAuthUser(authUserEntity);

		return R.ok(true);

    }
}




