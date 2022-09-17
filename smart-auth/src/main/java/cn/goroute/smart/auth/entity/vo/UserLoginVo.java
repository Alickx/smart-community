package cn.goroute.smart.auth.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Alickx
 */
@Data
public class UserLoginVo {



    @Pattern(regexp = "^([A-Za-z0-9_\\-\\.\\u4e00-\\u9fa5])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,8})$", message = "用户名格式不正确")
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9_-]{6,18}$", message = "密码格式不正确")
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @NotBlank(message="[手机号 邮箱 用户名或第三方应用的唯一标识]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("手机号 邮箱 用户名或第三方应用的唯一标识")
    @Length(max= 50,message="编码长度不能超过50")
    private String identifier;

}
