package cn.goroute.smart.auth.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.time.LocalDateTime;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 用户注册日志表
* @TableName auth_register_log
*/
public class AuthRegisterLog implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    /**
    * 注册方式1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博
    */
    @NotNull(message="[注册方式1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博]不能为空")
    @ApiModelProperty("注册方式1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博")
    private Integer registerMethod;
    /**
    * 注册时间
    */
    @NotNull(message="[注册时间]不能为空")
    @ApiModelProperty("注册时间")
    private LocalDateTime registerTime;
    /**
    * 注册IP
    */
    @NotBlank(message="[注册IP]不能为空")
    @Size(max= 16,message="编码长度不能超过16")
    @ApiModelProperty("注册IP")
    @Length(max= 16,message="编码长度不能超过16")
    private String registerIp;
    /**
    * 注册客户端
    */
    @NotBlank(message="[注册客户端]不能为空")
    @Size(max= 16,message="编码长度不能超过16")
    @ApiModelProperty("注册客户端")
    @Length(max= 16,message="编码长度不能超过16")
    private String registerClient;

    /**
    * 
    */
    private void setId(Long id){
    this.id = id;
    }

    /**
    * 注册方式1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博
    */
    private void setRegisterMethod(Integer registerMethod){
    this.registerMethod = registerMethod;
    }

    /**
    * 注册时间
    */
    private void setRegisterTime(LocalDateTime registerTime){
    this.registerTime = registerTime;
    }

    /**
    * 注册IP
    */
    private void setRegisterIp(String registerIp){
    this.registerIp = registerIp;
    }

    /**
    * 注册客户端
    */
    private void setRegisterClient(String registerClient){
    this.registerClient = registerClient;
    }


    /**
    * 
    */
    private Long getId(){
    return this.id;
    }

    /**
    * 注册方式1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博
    */
    private Integer getRegisterMethod(){
    return this.registerMethod;
    }

    /**
    * 注册时间
    */
    private LocalDateTime getRegisterTime(){
    return this.registerTime;
    }

    /**
    * 注册IP
    */
    private String getRegisterIp(){
    return this.registerIp;
    }

    /**
    * 注册客户端
    */
    private String getRegisterClient(){
    return this.registerClient;
    }

}
