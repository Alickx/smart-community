package cn.goroute.smart.user.domain;

import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.time.LocalDateTime;
import io.swagger.annotations.ApiModelProperty;

/**
* 用户关注表
* @TableName user_follow
*/
public class UserFollow implements Serializable {

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
    * 关注目标的用户id
    */
    @NotNull(message="[关注目标的用户id]不能为空")
    @ApiModelProperty("关注目标的用户id")
    private Long toUserId;
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
    * 关注目标的用户id
    */
    private void setToUserId(Long toUserId){
    this.toUserId = toUserId;
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
    * 关注目标的用户id
    */
    private Long getToUserId(){
    return this.toUserId;
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
