package cn.goroute.smart.post.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 文章回复表
 * @TableName comment
 */
@TableName(value ="comment")
@Data
public class Comment implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 发送方id
     */
    private Long userId;

    /**
     * 文章id
     */
    private Long postId;

    /**
     * 接收方id
     */
    private Long toUserId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 状态 0 = 正常显示 1 = 隐藏
     */
    private Integer state;

    /**
     * 回复类型 0 = 一级评论 1 = 评论中回复
     */
    private Integer type;

    /**
     * 一级评论uid
     */
    private Long firstCommentId;

    /**
     * 
     */
    private LocalDateTime createTime;

    /**
     * 
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标记 0 = 正常 1 = 删除
     */
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}