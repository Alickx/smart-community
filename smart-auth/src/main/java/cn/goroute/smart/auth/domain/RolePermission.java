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
* @TableName role_permission
*/
public class RolePermission implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    /**
    * 角色id
    */
    @NotNull(message="[角色id]不能为空")
    @ApiModelProperty("角色id")
    private Long roleId;
    /**
    * 权限id
    */
    @NotNull(message="[权限id]不能为空")
    @ApiModelProperty("权限id")
    private Long permissionUid;
    /**
    * 创建时间
    */
    @NotNull(message="[创建时间]不能为空")
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    /**
    * 更新时间
    */
    @NotNull(message="[更新时间]不能为空")
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    /**
    * 
    */
    private void setId(Long id){
    this.id = id;
    }

    /**
    * 角色id
    */
    private void setRoleId(Long roleId){
    this.roleId = roleId;
    }

    /**
    * 权限id
    */
    private void setPermissionUid(Long permissionUid){
    this.permissionUid = permissionUid;
    }

    /**
    * 创建时间
    */
    private void setCreateTime(LocalDateTime createTime){
    this.createTime = createTime;
    }

    /**
    * 更新时间
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
    * 角色id
    */
    private Long getRoleId(){
    return this.roleId;
    }

    /**
    * 权限id
    */
    private Long getPermissionUid(){
    return this.permissionUid;
    }

    /**
    * 创建时间
    */
    private LocalDateTime getCreateTime(){
    return this.createTime;
    }

    /**
    * 更新时间
    */
    private LocalDateTime getUpdateTime(){
    return this.updateTime;
    }

}
