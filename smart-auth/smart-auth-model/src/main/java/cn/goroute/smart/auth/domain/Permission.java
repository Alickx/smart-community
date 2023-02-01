package cn.goroute.smart.auth.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 
* @TableName permission
*/
@Data
public class Permission implements Serializable {

    /**
    * 
    */
    private Long id;
    /**
    * 
    */
    @Size(max= 50,message="编码长度不能超过50")
    @Length(max= 50,message="编码长度不能超过50")
    private String permissionName;
    /**
    * 
    */
    @Size(max= 255,message="编码长度不能超过255")
    @Length(max= 255,message="编码长度不能超过255")
    private String url;
    /**
    * 
    */
    @Size(max= 255,message="编码长度不能超过255")
    @Length(max= 255,message="编码长度不能超过255")
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
