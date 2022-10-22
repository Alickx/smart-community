package cn.goroute.smart.auth.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 用户注册日志表
* @TableName auth_register_log
*/
@Data
public class AuthRegisterLog implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    private Long id;
    /**
    * 注册方式1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博
    */
    @NotNull(message="[注册方式1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博]不能为空")
    private Integer registerMethod;
    /**
    * 注册时间
    */
    @NotNull(message="[注册时间]不能为空")
    private LocalDateTime registerTime;
    /**
    * 注册IP
    */
    @NotBlank(message="[注册IP]不能为空")
    @Size(max= 16,message="编码长度不能超过16")
    @Length(max= 16,message="编码长度不能超过16")
    private String registerIp;
    /**
    * 注册客户端
    */
    @NotBlank(message="[注册客户端]不能为空")
    @Size(max= 16,message="编码长度不能超过16")
    @Length(max= 16,message="编码长度不能超过16")
    private String registerClient;
}
