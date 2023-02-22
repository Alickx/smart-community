package cn.goroute.smart.auth.domain.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 
* @TableName user_role
*/
@Data
public class UserRoleEntity implements Serializable {

    /**
    * 
    */
    private Long id;
    /**
    * 用户id
    */
    private Long userId;
    /**
    * 角色id
    */
    private Long roleId;
    /**
    * 创建时间
    */
    private LocalDateTime createdTime;
    /**
    * 更新时间
    */
    private LocalDateTime updatedTime;
}
