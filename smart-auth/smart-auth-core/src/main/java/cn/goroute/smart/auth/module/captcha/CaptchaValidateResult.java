package cn.goroute.smart.auth.module.captcha;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * @author: Alickx
 * @Date: 2023/02/18 11:50:09
 * @Description: 验证码校验结果
 */
@Getter
@Accessors(chain = true)
public class CaptchaValidateResult {

    /**
     * 是否成功
     */
    private final boolean success;

    /**
     * 信息
     */
    private final String message;

    public CaptchaValidateResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static CaptchaValidateResult success() {
        return success("success");
    }

    public static CaptchaValidateResult success(String successMessage) {
        return new CaptchaValidateResult(true, successMessage);
    }

    public static CaptchaValidateResult failure() {
        return failure("captcha validate failure");
    }

    public static CaptchaValidateResult failure(String failureMessage) {
        return new CaptchaValidateResult(false, failureMessage);
    }

}
