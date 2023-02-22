package cn.goroute.smart.auth.module.captcha;

import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.spring.application.ImageCaptchaApplication;
import cloud.tianai.captcha.spring.vo.CaptchaResponse;
import cloud.tianai.captcha.spring.vo.ImageCaptchaVO;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/captcha/tianai")
@RequiredArgsConstructor
public class TianaiCaptchaEndpoint {

	private final ImageCaptchaApplication imageCaptchaApplication;

	@GetMapping("/gen")
	@ResponseBody
	public CaptchaResponse<ImageCaptchaVO> genCaptcha(@RequestParam(value = "type", required = false) String type) {
		if (StringUtils.isBlank(type)) {
			type = CaptchaTypeConstant.SLIDER;
		}
		return imageCaptchaApplication.generateCaptcha(type);
	}

	@PostMapping("/check")
	@ResponseBody
	public boolean checkCaptcha(@RequestParam("id") String id, @RequestBody ImageCaptchaTrack imageCaptchaTrack) {
		return imageCaptchaApplication.matching(id, imageCaptchaTrack);
	}

}
