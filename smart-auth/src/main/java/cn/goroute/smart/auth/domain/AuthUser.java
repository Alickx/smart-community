package cn.goroute.smart.auth.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 用户授权表
* @TableName auth_user
*/
@Data
public class AuthUser implements Serializable {

    /**
    * 
    */
    @NotNull(message="[主键id]不能为空")
    @ApiModelProperty("")
    private Long id;
    /**
    * 1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博
    */
    @NotNull(message="[渠道类型]不能为空")
    @ApiModelProperty("1手机号 2邮箱 3用户名 4qq 5微信")
    private Integer identityType;
    /**
    * 手机号 邮箱 用户名或第三方应用的唯一标识
    */
    @NotBlank(message="[手机号 邮箱 用户名或第三方应用的唯一标识]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("手机号 邮箱 用户名或第三方应用的唯一标识")
    @Length(max= 50,message="编码长度不能超过50")
    private String identifier;
    /**
    * 密码凭证(站内的保存密码，站外的不保存或保存token)
    */
    @NotBlank(message="[密码凭证(站内的保存密码，站外的不保存或保存token)]不能为空")
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("密码凭证(站内的保存密码，站外的不保存或保存token)")
    @Length(max= 20,message="编码长度不能超过20")
    private String certificate;
    /**
    * 绑定时间
    */
    @NotNull(message="[绑定时间]不能为空")
    @ApiModelProperty("绑定时间")
    private LocalDateTime createTime;
    /**
    * 更新绑定时间
    */
    @NotNull(message="[更新绑定时间]不能为空")
    @ApiModelProperty("更新绑定时间")
    private LocalDateTime updateTime;
}
