package cn.goroute.smart.auth.domain.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 
* @TableName permission
*/
@Data
public class PermissionEntity implements Serializable {

    /**
    * 
    */
    private Long id;
    /**
    * 
    */
    private String permissionName;
    /**
    * 
    */
    private String url;
    /**
    * 
    */
    private String description;
    /**
    * 
    */
    private LocalDateTime updateTime;
    /**
    * 
    */
    private LocalDateTime createTime;

}
