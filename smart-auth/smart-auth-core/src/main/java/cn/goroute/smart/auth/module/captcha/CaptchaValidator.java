package cn.goroute.smart.auth.module.captcha;

public interface CaptchaValidator {

    /**
     * 校验验证码
     * @param captchaId 验证码id
     * @return {@link CaptchaValidateResult}
     */
    CaptchaValidateResult validate(String captchaId);

}
