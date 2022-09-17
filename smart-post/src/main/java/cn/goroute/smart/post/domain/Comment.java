package cn.goroute.smart.post.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.time.LocalDateTime;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 文章回复表
* @TableName comment
*/
public class Comment implements Serializable {

    /**
    * 主键id
    */
    @NotNull(message="[主键id]不能为空")
    @ApiModelProperty("主键id")
    private Long id;
    /**
    * 发送方id
    */
    @ApiModelProperty("发送方id")
    private Long userId;
    /**
    * 文章id
    */
    @ApiModelProperty("文章id")
    private Long postId;
    /**
    * 接收方id
    */
    @ApiModelProperty("接收方id")
    private Long toUserId;
    /**
    * 评论内容
    */
    @Size(max= 2048,message="编码长度不能超过2048")
    @ApiModelProperty("评论内容")
    @Length(max= 2,048,message="编码长度不能超过2,048")
    private String content;
    /**
    * 状态
    */
    @NotNull(message="[状态]不能为空")
    @ApiModelProperty("状态")
    private Integer state;
    /**
    * 回复类型 0 = 一级评论 1 = 评论中回复
    */
    @NotNull(message="[回复类型 0 = 一级评论 1 = 评论中回复]不能为空")
    @ApiModelProperty("回复类型 0 = 一级评论 1 = 评论中回复")
    private Integer type;
    /**
    * 一级评论uid
    */
    @ApiModelProperty("一级评论uid")
    private Long firstCommentId;
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
    * 主键id
    */
    private void setId(Long id){
    this.id = id;
    }

    /**
    * 发送方id
    */
    private void setUserId(Long userId){
    this.userId = userId;
    }

    /**
    * 文章id
    */
    private void setPostId(Long postId){
    this.postId = postId;
    }

    /**
    * 接收方id
    */
    private void setToUserId(Long toUserId){
    this.toUserId = toUserId;
    }

    /**
    * 评论内容
    */
    private void setContent(String content){
    this.content = content;
    }

    /**
    * 状态
    */
    private void setState(Integer state){
    this.state = state;
    }

    /**
    * 回复类型 0 = 一级评论 1 = 评论中回复
    */
    private void setType(Integer type){
    this.type = type;
    }

    /**
    * 一级评论uid
    */
    private void setFirstCommentId(Long firstCommentId){
    this.firstCommentId = firstCommentId;
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
    * 主键id
    */
    private Long getId(){
    return this.id;
    }

    /**
    * 发送方id
    */
    private Long getUserId(){
    return this.userId;
    }

    /**
    * 文章id
    */
    private Long getPostId(){
    return this.postId;
    }

    /**
    * 接收方id
    */
    private Long getToUserId(){
    return this.toUserId;
    }

    /**
    * 评论内容
    */
    private String getContent(){
    return this.content;
    }

    /**
    * 状态
    */
    private Integer getState(){
    return this.state;
    }

    /**
    * 回复类型 0 = 一级评论 1 = 评论中回复
    */
    private Integer getType(){
    return this.type;
    }

    /**
    * 一级评论uid
    */
    private Long getFirstCommentId(){
    return this.firstCommentId;
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
