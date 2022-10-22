package cn.goroute.smart.auth.entity.vo;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Alickx
 */
@Data
@Schema(name = "用户注册信息")
@ParameterObject
public class UserRegisterVO {

    @Pattern(regexp = "^([A-Za-z0-9_\\-\\.\\u4e00-\\u9fa5])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,8})$", message = "用户名格式不正确")
    @NotBlank(message = "用户名不能为空")
    @Parameter(description = "用户名")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9_-]{6,18}$", message = "密码格式不正确")
    @NotBlank(message = "密码不能为空")
    @Parameter(description = "密码")
    private String password;

    @NotNull(message="[渠道类型]不能为空")
    @Parameter(description = "渠道类型")
    private Integer identityType;

}
