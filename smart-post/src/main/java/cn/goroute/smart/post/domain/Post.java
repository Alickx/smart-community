package cn.goroute.smart.post.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 文章表
* @TableName post
*/
public class Post implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    /**
    * 
    */
    @ApiModelProperty("")
    private Long categoryId;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long userId;
    /**
    * 
    */
    @NotBlank(message="[]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("")
    @Length(max= 255,message="编码长度不能超过255")
    private String title;
    /**
    * 
    */
    @NotBlank(message="[]不能为空")
    @Size(max= -1,message="编码长度不能超过-1")
    @ApiModelProperty("")
    @Length(max= -1,message="编码长度不能超过-1")
    private String content;
    /**
    * 文章状态 0 = 正常
    */
    @NotNull(message="[文章状态 0 = 正常]不能为空")
    @ApiModelProperty("文章状态 0 = 正常")
    private Integer state;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Integer collectCount;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Integer thumbCount;
    /**
    * 0 = 不公布  1 = 公布
    */
    @NotBlank(message="[0 = 不公布  1 = 公布]不能为空")
    @Size(max= 1,message="编码长度不能超过1")
    @ApiModelProperty("0 = 不公布  1 = 公布")
    @Length(max= 1,message="编码长度不能超过1")
    private String isPublish;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Integer commentCount;
    /**
    * 
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("")
    @Length(max= 255,message="编码长度不能超过255")
    private String summary;
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
    private LocalDateTime createdTime;
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
    private void setCategoryId(Long categoryId){
    this.categoryId = categoryId;
    }

    /**
    * 
    */
    private void setUserId(Long userId){
    this.userId = userId;
    }

    /**
    * 
    */
    private void setTitle(String title){
    this.title = title;
    }

    /**
    * 
    */
    private void setContent(String content){
    this.content = content;
    }

    /**
    * 文章状态 0 = 正常
    */
    private void setStatus(Integer status){
    this.state = status;
    }

    /**
    * 
    */
    private void setCollectCount(Integer collectCount){
    this.collectCount = collectCount;
    }

    /**
    * 
    */
    private void setThumbCount(Integer thumbCount){
    this.thumbCount = thumbCount;
    }

    /**
    * 0 = 不公布  1 = 公布
    */
    private void setIsPublish(String isPublish){
    this.isPublish = isPublish;
    }

    /**
    * 
    */
    private void setCommentCount(Integer commentCount){
    this.commentCount = commentCount;
    }

    /**
    * 
    */
    private void setSummary(String summary){
    this.summary = summary;
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
    private void setCreatedTime(LocalDateTime createdTime){
    this.createdTime = createdTime;
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
    private Long getCategoryId(){
    return this.categoryId;
    }

    /**
    * 
    */
    private Long getUserId(){
    return this.userId;
    }

    /**
    * 
    */
    private String getTitle(){
    return this.title;
    }

    /**
    * 
    */
    private String getContent(){
    return this.content;
    }

    /**
    * 文章状态 0 = 正常
    */
    private Integer getStatus(){
    return this.state;
    }

    /**
    * 
    */
    private Integer getCollectCount(){
    return this.collectCount;
    }

    /**
    * 
    */
    private Integer getThumbCount(){
    return this.thumbCount;
    }

    /**
    * 0 = 不公布  1 = 公布
    */
    private String getIsPublish(){
    return this.isPublish;
    }

    /**
    * 
    */
    private Integer getCommentCount(){
    return this.commentCount;
    }

    /**
    * 
    */
    private String getSummary(){
    return this.summary;
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
    private LocalDateTime getCreatedTime(){
    return this.createdTime;
    }

    /**
    * 
    */
    private Integer getDeleted(){
    return this.deleted;
    }

}
