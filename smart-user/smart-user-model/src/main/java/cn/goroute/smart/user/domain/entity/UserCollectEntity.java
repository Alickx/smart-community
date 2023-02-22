package cn.goroute.smart.user.domain.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 用户收藏表
* @TableName user_collect
*/
@Data
public class UserCollectEntity implements Serializable {

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
    * 文章id
    */
    @NotNull(message="[文章id]不能为空")
    private Long postId;
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
