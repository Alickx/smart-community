package cn.goroute.smart.user.domain;

import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.time.LocalDateTime;
import io.swagger.annotations.ApiModelProperty;

/**
* 用户收藏表
* @TableName user_collect
*/
public class UserCollect implements Serializable {

    /**
    * 主键id
    */
    @NotNull(message="[主键id]不能为空")
    @ApiModelProperty("主键id")
    private Long id;
    /**
    * 用户id
    */
    @NotNull(message="[用户id]不能为空")
    @ApiModelProperty("用户id")
    private Long userId;
    /**
    * 文章id
    */
    @NotNull(message="[文章id]不能为空")
    @ApiModelProperty("文章id")
    private Long postId;
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
    * 用户id
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
    * 用户id
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
