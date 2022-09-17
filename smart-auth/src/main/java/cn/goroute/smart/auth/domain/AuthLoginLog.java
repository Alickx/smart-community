package cn.goroute.smart.auth.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.time.LocalDateTime;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 登陆日志表
* @TableName auth_login_log
*/
public class AuthLoginLog implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    /**
    * 登录方式 第三方/邮箱/手机等
    */
    @NotNull(message="[登录方式 第三方/邮箱/手机等]不能为空")
    @ApiModelProperty("登录方式 第三方/邮箱/手机等")
    private Integer type;
    /**
    * 操作类型 1登陆成功  2登出成功 3登录失败 4登出失败
    */
    @NotNull(message="[操作类型 1登陆成功  2登出成功 3登录失败 4登出失败]不能为空")
    @ApiModelProperty("操作类型 1登陆成功  2登出成功 3登录失败 4登出失败")
    private Integer command;
    /**
    * 客户端版本号
    */
    @NotBlank(message="[客户端版本号]不能为空")
    @Size(max= 32,message="编码长度不能超过32")
    @ApiModelProperty("客户端版本号")
    @Length(max= 32,message="编码长度不能超过32")
    private String version;
    /**
    * 客户端
    */
    @NotBlank(message="[客户端]不能为空")
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("客户端")
    @Length(max= 20,message="编码长度不能超过20")
    private String client;
    /**
    * 登录时设备号
    */
    @NotBlank(message="[登录时设备号]不能为空")
    @Size(max= 64,message="编码长度不能超过64")
    @ApiModelProperty("登录时设备号")
    @Length(max= 64,message="编码长度不能超过64")
    private String deviceId;
    /**
    * 登录ip
    */
    @NotBlank(message="[登录ip]不能为空")
    @Size(max= 32,message="编码长度不能超过32")
    @ApiModelProperty("登录ip")
    @Length(max= 32,message="编码长度不能超过32")
    private String lastip;
    /**
    * 手机系统
    */
    @NotBlank(message="[手机系统]不能为空")
    @Size(max= 16,message="编码长度不能超过16")
    @ApiModelProperty("手机系统")
    @Length(max= 16,message="编码长度不能超过16")
    private String os;
    /**
    * 系统版本
    */
    @NotBlank(message="[系统版本]不能为空")
    @Size(max= 32,message="编码长度不能超过32")
    @ApiModelProperty("系统版本")
    @Length(max= 32,message="编码长度不能超过32")
    private String osver;
    /**
    * 
    */
    @NotBlank(message="[]不能为空")
    @Size(max= 200,message="编码长度不能超过200")
    @ApiModelProperty("")
    @Length(max= 200,message="编码长度不能超过200")
    private String text;
    /**
    * 操作时间
    */
    @NotNull(message="[操作时间]不能为空")
    @ApiModelProperty("操作时间")
    private LocalDateTime createTime;

    /**
    * 
    */
    private void setId(Long id){
    this.id = id;
    }

    /**
    * 登录方式 第三方/邮箱/手机等
    */
    private void setType(Integer type){
    this.type = type;
    }

    /**
    * 操作类型 1登陆成功  2登出成功 3登录失败 4登出失败
    */
    private void setCommand(Integer command){
    this.command = command;
    }

    /**
    * 客户端版本号
    */
    private void setVersion(String version){
    this.version = version;
    }

    /**
    * 客户端
    */
    private void setClient(String client){
    this.client = client;
    }

    /**
    * 登录时设备号
    */
    private void setDeviceId(String deviceId){
    this.deviceId = deviceId;
    }

    /**
    * 登录ip
    */
    private void setLastip(String lastip){
    this.lastip = lastip;
    }

    /**
    * 手机系统
    */
    private void setOs(String os){
    this.os = os;
    }

    /**
    * 系统版本
    */
    private void setOsver(String osver){
    this.osver = osver;
    }

    /**
    * 
    */
    private void setText(String text){
    this.text = text;
    }

    /**
    * 操作时间
    */
    private void setCreateTime(LocalDateTime createTime){
    this.createTime = createTime;
    }


    /**
    * 
    */
    private Long getId(){
    return this.id;
    }

    /**
    * 登录方式 第三方/邮箱/手机等
    */
    private Integer getType(){
    return this.type;
    }

    /**
    * 操作类型 1登陆成功  2登出成功 3登录失败 4登出失败
    */
    private Integer getCommand(){
    return this.command;
    }

    /**
    * 客户端版本号
    */
    private String getVersion(){
    return this.version;
    }

    /**
    * 客户端
    */
    private String getClient(){
    return this.client;
    }

    /**
    * 登录时设备号
    */
    private String getDeviceId(){
    return this.deviceId;
    }

    /**
    * 登录ip
    */
    private String getLastip(){
    return this.lastip;
    }

    /**
    * 手机系统
    */
    private String getOs(){
    return this.os;
    }

    /**
    * 系统版本
    */
    private String getOsver(){
    return this.osver;
    }

    /**
    * 
    */
    private String getText(){
    return this.text;
    }

    /**
    * 操作时间
    */
    private LocalDateTime getCreateTime(){
    return this.createTime;
    }

}
