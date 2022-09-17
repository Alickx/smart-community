package cn.goroute.smart.post.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.time.LocalDateTime;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 文章标签表
* @TableName post_tag
*/
public class PostTag implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long postId;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long tagId;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private LocalDateTime createdTime;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private LocalDateTime updatedTime;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Integer deleted;

    /**
    * 
    */
    private void setId(Long id){
    this.id = id;
    }

    /**
    * 
    */
    private void setPostId(Long postId){
    this.postId = postId;
    }

    /**
    * 
    */
    private void setTagId(Long tagId){
    this.tagId = tagId;
    }

    /**
    * 
    */
    private void setCreatedTime(LocalDateTime createdTime){
    this.createdTime = createdTime;
    }

    /**
    * 
    */
    private void setUpdatedTime(LocalDateTime updatedTime){
    this.updatedTime = updatedTime;
    }

    /**
    * 
    */
    private void setDeleted(Integer deleted){
    this.deleted = deleted;
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
    private Long getPostId(){
    return this.postId;
    }

    /**
    * 
    */
    private Long getTagId(){
    return this.tagId;
    }

    /**
    * 
    */
    private LocalDateTime getCreatedTime(){
    return this.createdTime;
    }

    /**
    * 
    */
    private LocalDateTime getUpdatedTime(){
    return this.updatedTime;
    }

    /**
    * 
    */
    private Integer getDeleted(){
    return this.deleted;
    }

}
