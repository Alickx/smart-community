package cn.goroute.smart.post.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.time.LocalDateTime;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 点赞表
* @TableName thumb
*/
public class Thumb implements Serializable {

    /**
    * 主键id
    */
    @NotNull(message="[主键id]不能为空")
    @ApiModelProperty("主键id")
    private Long id;
    /**
    * 点赞用户uid
    */
    @NotNull(message="[点赞用户uid]不能为空")
    @ApiModelProperty("点赞用户uid")
    private Long userId;
    /**
    * 点赞目标的id
    */
    @NotNull(message="[点赞目标的id]不能为空")
    @ApiModelProperty("点赞目标的id")
    private Long toUserId;
    /**
    * 点赞所在的帖子uid
    */
    @NotNull(message="[点赞所在的帖子uid]不能为空")
    @ApiModelProperty("点赞所在的帖子uid")
    private Long postId;
    /**
    * 点赞内容的uid 评论uid或是文章uid
    */
    @NotNull(message="[点赞内容的uid 评论uid或是文章uid]不能为空")
    @ApiModelProperty("点赞内容的uid 评论uid或是文章uid")
    private Long toId;
    /**
    * 点赞类型 0 = 评论 1 = 文章
    */
    @NotNull(message="[点赞类型 0 = 评论 1 = 文章]不能为空")
    @ApiModelProperty("点赞类型 0 = 评论 1 = 文章")
    private Integer type;
    /**
    * 点赞时间
    */
    @NotNull(message="[点赞时间]不能为空")
    @ApiModelProperty("点赞时间")
    private LocalDateTime createTime;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private LocalDateTime updateTime;
    /**
    * 0 = 正常 1 = 已取消点赞
    */
    @NotNull(message="[0 = 正常 1 = 已取消点赞]不能为空")
    @ApiModelProperty("0 = 正常 1 = 已取消点赞")
    private Integer state;

    /**
    * 主键id
    */
    private void setId(Long id){
    this.id = id;
    }

    /**
    * 点赞用户uid
    */
    private void setUserId(Long userId){
    this.userId = userId;
    }

    /**
    * 点赞目标的id
    */
    private void setToUserId(Long toUserId){
    this.toUserId = toUserId;
    }

    /**
    * 点赞所在的帖子uid
    */
    private void setPostId(Long postId){
    this.postId = postId;
    }

    /**
    * 点赞内容的uid 评论uid或是文章uid
    */
    private void setToId(Long toId){
    this.toId = toId;
    }

    /**
    * 点赞类型 0 = 评论 1 = 文章
    */
    private void setType(Integer type){
    this.type = type;
    }

    /**
    * 点赞时间
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
    * 0 = 正常 1 = 已取消点赞
    */
    private void setState(Integer state){
    this.state = state;
    }


    /**
    * 主键id
    */
    private Long getId(){
    return this.id;
    }

    /**
    * 点赞用户uid
    */
    private Long getUserId(){
    return this.userId;
    }

    /**
    * 点赞目标的id
    */
    private Long getToUserId(){
    return this.toUserId;
    }

    /**
    * 点赞所在的帖子uid
    */
    private Long getPostId(){
    return this.postId;
    }

    /**
    * 点赞内容的uid 评论uid或是文章uid
    */
    private Long getToId(){
    return this.toId;
    }

    /**
    * 点赞类型 0 = 评论 1 = 文章
    */
    private Integer getType(){
    return this.type;
    }

    /**
    * 点赞时间
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

    /**
    * 0 = 正常 1 = 已取消点赞
    */
    private Integer getState(){
    return this.state;
    }

}
