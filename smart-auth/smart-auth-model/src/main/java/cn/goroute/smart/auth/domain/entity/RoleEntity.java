package cn.goroute.smart.auth.domain.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 
* @TableName role
*/
@Data
public class RoleEntity implements Serializable {

    /**
    * 
    */
    private Long id;
    /**
    * 
    */
    private String roleName;
    /**
    * 
    */
    private String description;
    /**
    * 
    */
    private LocalDateTime createTime;
    /**
    * 
    */
    private LocalDateTime updateTime;
}
