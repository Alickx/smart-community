package cn.goroute.smart.auth.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.time.LocalDateTime;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 用户授权表
* @TableName auth_user
*/
public class AuthUser implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    /**
    * 1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博
    */
    @NotNull(message="[1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博]不能为空")
    @ApiModelProperty("1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博")
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

    /**
    * 
    */
    private void setId(Long id){
    this.id = id;
    }

    /**
    * 1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博
    */
    private void setIdentityType(Integer identityType){
    this.identityType = identityType;
    }

    /**
    * 手机号 邮箱 用户名或第三方应用的唯一标识
    */
    private void setIdentifier(String identifier){
    this.identifier = identifier;
    }

    /**
    * 密码凭证(站内的保存密码，站外的不保存或保存token)
    */
    private void setCertificate(String certificate){
    this.certificate = certificate;
    }

    /**
    * 绑定时间
    */
    private void setCreateTime(LocalDateTime createTime){
    this.createTime = createTime;
    }

    /**
    * 更新绑定时间
    */
    private void setUpdateTime(LocalDateTime updateTime){
    this.updateTime = updateTime;
    }


    /**
    * 
    */
    private Long getId(){
    return this.id;
    }

    /**
    * 1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博
    */
    private Integer getIdentityType(){
    return this.identityType;
    }

    /**
    * 手机号 邮箱 用户名或第三方应用的唯一标识
    */
    private String getIdentifier(){
    return this.identifier;
    }

    /**
    * 密码凭证(站内的保存密码，站外的不保存或保存token)
    */
    private String getCertificate(){
    return this.certificate;
    }

    /**
    * 绑定时间
    */
    private LocalDateTime getCreateTime(){
    return this.createTime;
    }

    /**
    * 更新绑定时间
    */
    private LocalDateTime getUpdateTime(){
    return this.updateTime;
    }

}
