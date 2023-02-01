package cn.goroute.smart.auth.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 
* @TableName role
*/
@Data
public class Role implements Serializable {

    /**
    * 
    */
    private Long id;
    /**
    * 
    */
    @Size(max= 50,message="编码长度不能超过50")
    @Length(max= 50,message="编码长度不能超过50")
    private String roleName;
    /**
    * 
    */
    @Size(max= 255,message="编码长度不能超过255")
    @Length(max= 255,message="编码长度不能超过255")
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
