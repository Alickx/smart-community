package cn.goroute.smart.auth.module.captcha;

import cloud.tianai.captcha.spring.application.ImageCaptchaApplication;
import cloud.tianai.captcha.spring.plugins.secondary.SecondaryVerificationApplication;
import cn.hutool.core.text.CharSequenceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * tianai 验证码的校验器
 */
@Component
@Primary
@RequiredArgsConstructor
public class TianaiCaptchaValidator implements CaptchaValidator {

	private final ImageCaptchaApplication sca;

	@Override
	public CaptchaValidateResult validate(String  captchaId) {

		if (CharSequenceUtil.isBlank(captchaId)) {
			return CaptchaValidateResult.failure("captcha id can not be null");
		}

		if (!(sca instanceof SecondaryVerificationApplication)) {
			return CaptchaValidateResult.failure("captcha must enable secondary verification");
		}

		boolean match = ((SecondaryVerificationApplication) sca).secondaryVerification(captchaId);
		return match ? CaptchaValidateResult.success() : CaptchaValidateResult.failure("captcha validate failure");
	}

}