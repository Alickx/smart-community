package cn.goroute.smart.auth.domain.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Alickx
 */
@Data
public class UserRegisterForm {

	/**
	 * 用户名
	 */
    @NotBlank(message = "用户名不能为空")
    private String userName;

	/**
	 * 密码
	 */
    @NotBlank(message = "密码不能为空")
    private String password;

	/**
	 * 邮箱
	 */
	@NotBlank(message = "邮箱不能为空")
	@Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", message = "邮箱格式不正确")
	private String userEmail;

	/**
	 * 验证码id
	 */
	@NotBlank(message = "验证码id不能为空")
	private String captchaId;
}
