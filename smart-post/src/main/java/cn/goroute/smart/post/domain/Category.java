package cn.goroute.smart.post.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.time.LocalDateTime;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 板块表
* @TableName category
*/
public class Category implements Serializable {

    /**
    * 板块id
    */
    @NotNull(message="[板块id]不能为空")
    @ApiModelProperty("板块id")
    private Long id;
    /**
    * 板块名称
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("板块名称")
    @Length(max= 255,message="编码长度不能超过255")
    private String name;
    /**
    * 板块图标
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("板块图标")
    @Length(max= 255,message="编码长度不能超过255")
    private String icon;
    /**
    * 板块链接
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("板块链接")
    @Length(max= 255,message="编码长度不能超过255")
    private String url;
    /**
    * 板块介绍
    */
    @Size(max= 2048,message="编码长度不能超过2048")
    @ApiModelProperty("板块介绍")
    @Length(max= 2,048,message="编码长度不能超过2,048")
    private String intro;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private LocalDateTime updateTime;
    /**
    * 创建时间
    */
    @NotNull(message="[创建时间]不能为空")
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    /**
    * 板块id
    */
    private void setId(Long id){
    this.id = id;
    }

    /**
    * 板块名称
    */
    private void setName(String name){
    this.name = name;
    }

    /**
    * 板块图标
    */
    private void setIcon(String icon){
    this.icon = icon;
    }

    /**
    * 板块链接
    */
    private void setUrl(String url){
    this.url = url;
    }

    /**
    * 板块介绍
    */
    private void setIntro(String intro){
    this.intro = intro;
    }

    /**
    * 
    */
    private void setUpdateTime(LocalDateTime updateTime){
    this.updateTime = updateTime;
    }

    /**
    * 创建时间
    */
    private void setCreateTime(LocalDateTime createTime){
    this.createTime = createTime;
    }


    /**
    * 板块id
    */
    private Long getId(){
    return this.id;
    }

    /**
    * 板块名称
    */
    private String getName(){
    return this.name;
    }

    /**
    * 板块图标
    */
    private String getIcon(){
    return this.icon;
    }

    /**
    * 板块链接
    */
    private String getUrl(){
    return this.url;
    }

    /**
    * 板块介绍
    */
    private String getIntro(){
    return this.intro;
    }

    /**
    * 
    */
    private LocalDateTime getUpdateTime(){
    return this.updateTime;
    }

    /**
    * 创建时间
    */
    private LocalDateTime getCreateTime(){
    return this.createTime;
    }

}
