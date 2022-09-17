package cn.goroute.smart.auth.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Alickx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterVo {

    @Pattern(regexp = "^([A-Za-z0-9_\\-\\.\\u4e00-\\u9fa5])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,8})$", message = "用户名格式不正确")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9_-]{6,18}$", message = "密码格式不正确")
    @NotBlank(message = "密码不能为空")
    private String passWord;

    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{6}$", message = "验证码格式不正确")
    private String captcha;

}
