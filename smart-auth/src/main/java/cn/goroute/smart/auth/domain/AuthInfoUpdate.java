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
* @TableName auth_info_update
*/
public class AuthInfoUpdate implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    /**
    * 属性名
    */
    @NotBlank(message="[属性名]不能为空")
    @Size(max= 30,message="编码长度不能超过30")
    @ApiModelProperty("属性名")
    @Length(max= 30,message="编码长度不能超过30")
    private String attributeName;
    /**
    * 属性对应旧的值
    */
    @NotBlank(message="[属性对应旧的值]不能为空")
    @Size(max= 30,message="编码长度不能超过30")
    @ApiModelProperty("属性对应旧的值")
    @Length(max= 30,message="编码长度不能超过30")
    private String attributeOldVal;
    /**
    * 属性对应新的值
    */
    @NotBlank(message="[属性对应新的值]不能为空")
    @Size(max= 30,message="编码长度不能超过30")
    @ApiModelProperty("属性对应新的值")
    @Length(max= 30,message="编码长度不能超过30")
    private String attributeNewVal;
    /**
    * 修改时间
    */
    @NotNull(message="[修改时间]不能为空")
    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

    /**
    * 
    */
    private void setId(Long id){
    this.id = id;
    }

    /**
    * 属性名
    */
    private void setAttributeName(String attributeName){
    this.attributeName = attributeName;
    }

    /**
    * 属性对应旧的值
    */
    private void setAttributeOldVal(String attributeOldVal){
    this.attributeOldVal = attributeOldVal;
    }

    /**
    * 属性对应新的值
    */
    private void setAttributeNewVal(String attributeNewVal){
    this.attributeNewVal = attributeNewVal;
    }

    /**
    * 修改时间
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
    * 属性名
    */
    private String getAttributeName(){
    return this.attributeName;
    }

    /**
    * 属性对应旧的值
    */
    private String getAttributeOldVal(){
    return this.attributeOldVal;
    }

    /**
    * 属性对应新的值
    */
    private String getAttributeNewVal(){
    return this.attributeNewVal;
    }

    /**
    * 修改时间
    */
    private LocalDateTime getUpdateTime(){
    return this.updateTime;
    }

}
