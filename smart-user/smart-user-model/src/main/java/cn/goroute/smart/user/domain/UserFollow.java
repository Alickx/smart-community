package cn.goroute.smart.user.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 用户关注表
* @TableName user_follow
*/
@Data
public class UserFollow implements Serializable {

    /**
    * 主键id
    */
    @NotNull(message="[主键id]不能为空")
    private Long id;
    /**
    * 用户id
    */
    @NotNull(message="[用户id]不能为空")
    private Long userId;
    /**
    * 关注目标的用户id
    */
    @NotNull(message="[关注目标的用户id]不能为空")
    private Long toUserId;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    private LocalDateTime createTime;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    private LocalDateTime updateTime;
}
