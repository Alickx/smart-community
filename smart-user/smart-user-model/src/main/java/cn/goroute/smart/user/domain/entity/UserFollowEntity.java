package cn.goroute.smart.user.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 用户关注表
* @TableName user_follow
*/
@Data
@Builder
@TableName(value ="user_follow")
public class UserFollowEntity implements Serializable {

    /**
    * 主键id
    */
    @TableId
    private Long id;
    /**
    * 用户id
    */
    private Long userId;
    /**
    * 关注目标的用户id
    */
    private Long toUserId;

    /**
     * 关注时间
     */
    private LocalDateTime followTime;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer deleted;

    /**
    *  创建时间
    */
    private LocalDateTime createTime;
    /**
    * 更新时间
    */
    private LocalDateTime updateTime;
}
