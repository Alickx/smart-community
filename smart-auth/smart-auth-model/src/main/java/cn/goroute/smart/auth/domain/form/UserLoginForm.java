package cn.goroute.smart.auth.domain.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Alickx
 */
@Data
public class UserLoginForm {

    /**
     * 用户名 / 邮箱
     */
    @NotBlank
    private String userName;

    /**
     * 密码
     */
    @NotBlank
    private String password;

    /**
     * 验证码id
     */
    @NotBlank
    private String captchaId;

}
