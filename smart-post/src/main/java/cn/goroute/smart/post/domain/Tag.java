package cn.goroute.smart.post.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.time.LocalDateTime;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 标签表
* @TableName tag
*/
public class Tag implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    /**
    * 
    */
    @NotBlank(message="[]不能为空")
    @Size(max= 1000,message="编码长度不能超过1000")
    @ApiModelProperty("")
    @Length(max= 1,000,message="编码长度不能超过1,000")
    private String content;
    /**
    * 0 = 正常
    */
    @ApiModelProperty("0 = 正常")
    private Integer state;
    /**
    * 
    */
    @Size(max= 2048,message="编码长度不能超过2048")
    @ApiModelProperty("")
    @Length(max= 2,048,message="编码长度不能超过2,048")
    private String intro;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Integer sort;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private LocalDateTime createTime;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private LocalDateTime updateTime;

    /**
    * 
    */
    private void setId(Long id){
    this.id = id;
    }

    /**
    * 
    */
    private void setContent(String content){
    this.content = content;
    }

    /**
    * 0 = 正常
    */
    private void setState(Integer state){
    this.state = state;
    }

    /**
    * 
    */
    private void setIntro(String intro){
    this.intro = intro;
    }

    /**
    * 
    */
    private void setSort(Integer sort){
    this.sort = sort;
    }

    /**
    * 
    */
    private void setCreateTime(LocalDateTime createTime){
    this.createTime = createTime;
    }

    /**
    * 
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
    * 
    */
    private String getContent(){
    return this.content;
    }

    /**
    * 0 = 正常
    */
    private Integer getState(){
    return this.state;
    }

    /**
    * 
    */
    private String getIntro(){
    return this.intro;
    }

    /**
    * 
    */
    private Integer getSort(){
    return this.sort;
    }

    /**
    * 
    */
    private LocalDateTime getCreateTime(){
    return this.createTime;
    }

    /**
    * 
    */
    private LocalDateTime getUpdateTime(){
    return this.updateTime;
    }

}
