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
* @TableName auth_info_update
*/
@Data
public class AuthInfoUpdate implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    private Long id;
    /**
    * 属性名
    */
    @NotBlank(message="[属性名]不能为空")
    @Size(max= 30,message="编码长度不能超过30")
    @Length(max= 30,message="编码长度不能超过30")
    private String attributeName;
    /**
    * 属性对应旧的值
    */
    @NotBlank(message="[属性对应旧的值]不能为空")
    @Size(max= 30,message="编码长度不能超过30")
    @Length(max= 30,message="编码长度不能超过30")
    private String attributeOldVal;
    /**
    * 属性对应新的值
    */
    @NotBlank(message="[属性对应新的值]不能为空")
    @Size(max= 30,message="编码长度不能超过30")
    @Length(max= 30,message="编码长度不能超过30")
    private String attributeNewVal;
    /**
    * 修改时间
    */
    @NotNull(message="[修改时间]不能为空")
    private LocalDateTime updateTime;


}
