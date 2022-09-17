package cn.goroute.smart.auth.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.time.LocalDateTime;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 
* @TableName user_role
*/
public class UserRole implements Serializable {

    /**
    * 
    */
    @ApiModelProperty("")
    private Long id;
    /**
    * 用户id
    */
    @ApiModelProperty("用户id")
    private Long userId;
    /**
    * 角色id
    */
    @ApiModelProperty("角色id")
    private Long roleId;
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private LocalDateTime createdTime;
    /**
    * 更新时间
    */
    @ApiModelProperty("更新时间")
    private LocalDateTime updatedTime;

    /**
    * 
    */
    private void setId(Long id){
    this.id = id;
    }

    /**
    * 用户id
    */
    private void setUserId(Long userId){
    this.userId = userId;
    }

    /**
    * 角色id
    */
    private void setRoleId(Long roleId){
    this.roleId = roleId;
    }

    /**
    * 创建时间
    */
    private void setCreatedTime(LocalDateTime createdTime){
    this.createdTime = createdTime;
    }

    /**
    * 更新时间
    */
    private void setUpdatedTime(LocalDateTime updatedTime){
    this.updatedTime = updatedTime;
    }


    /**
    * 
    */
    private Long getId(){
    return this.id;
    }

    /**
    * 用户id
    */
    private Long getUserId(){
    return this.userId;
    }

    /**
    * 角色id
    */
    private Long getRoleId(){
    return this.roleId;
    }

    /**
    * 创建时间
    */
    private LocalDateTime getCreatedTime(){
    return this.createdTime;
    }

    /**
    * 更新时间
    */
    private LocalDateTime getUpdatedTime(){
    return this.updatedTime;
    }

}
