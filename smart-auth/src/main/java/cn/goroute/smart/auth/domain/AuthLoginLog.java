package cn.goroute.smart.auth.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 登陆日志表
* @TableName auth_login_log
*/
@Data
public class AuthLoginLog implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    private Long id;
    /**
    * 登录方式 第三方/邮箱/手机等
    */
    @NotNull(message="[登录方式 第三方/邮箱/手机等]不能为空")
    private Integer type;
    /**
    * 操作类型 1登陆成功  2登出成功 3登录失败 4登出失败
    */
    @NotNull(message="[操作类型 1登陆成功  2登出成功 3登录失败 4登出失败]不能为空")
    private Integer command;
    /**
    * 客户端版本号
    */
    @NotBlank(message="[客户端版本号]不能为空")
    @Size(max= 32,message="编码长度不能超过32")
    @Length(max= 32,message="编码长度不能超过32")
    private String version;
    /**
    * 客户端
    */
    @NotBlank(message="[客户端]不能为空")
    @Size(max= 20,message="编码长度不能超过20")
    @Length(max= 20,message="编码长度不能超过20")
    private String client;
    /**
    * 登录时设备号
    */
    @NotBlank(message="[登录时设备号]不能为空")
    @Size(max= 64,message="编码长度不能超过64")
    @Length(max= 64,message="编码长度不能超过64")
    private String deviceId;
    /**
    * 登录ip
    */
    @NotBlank(message="[登录ip]不能为空")
    @Size(max= 32,message="编码长度不能超过32")
    @Length(max= 32,message="编码长度不能超过32")
    private String lastip;
    /**
    * 手机系统
    */
    @NotBlank(message="[手机系统]不能为空")
    @Size(max= 16,message="编码长度不能超过16")
    @Length(max= 16,message="编码长度不能超过16")
    private String os;
    /**
    * 系统版本
    */
    @NotBlank(message="[系统版本]不能为空")
    @Size(max= 32,message="编码长度不能超过32")
    @Length(max= 32,message="编码长度不能超过32")
    private String osver;
    /**
    * 
    */
    @NotBlank(message="[]不能为空")
    @Size(max= 200,message="编码长度不能超过200")
    @Length(max= 200,message="编码长度不能超过200")
    private String text;
    /**
    * 操作时间
    */
    @NotNull(message="[操作时间]不能为空")
    private LocalDateTime createTime;
}
