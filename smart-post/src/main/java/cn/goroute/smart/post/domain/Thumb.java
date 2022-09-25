package cn.goroute.smart.post.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 点赞表
 * @TableName thumb
 */
@TableName(value ="thumb")
@Data
public class Thumb implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 点赞用户id
     */
    private Long userId;

    /**
     * 点赞目标的id
     */
    private Long toUserId;

    /**
     * 点赞所在的帖子id
     */
    private Long postId;

    /**
     * 点赞内容的id
     */
    private Long toId;

    /**
     * 点赞类型 0 = 评论 1 = 文章
     */
    private Integer type;

    /**
     * 点赞时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}