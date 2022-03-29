package cn.goroute.smart.common.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文章回复表
 * @TableName t_comment
 */
@TableName(value ="t_comment")
@Data
public class Comment implements Serializable {
    /**
     * 主键uid
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String uid;

    /**
     * 用户uid
     */
    private String memberUid;

    /**
     * 文章uid
     */
    private String postUid;

    /**
     * 回复某个人的uid
     */
    private String toMemberUid;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 逻辑删除状态 0:未删除 1:已删除
     */
    private Integer status;

    /**
     * 0 = 一级评论 1 = 评论中回复
     */
    private Integer type;

    /**
     * 一级评论uid
     */
    private String firstCommentUid;

    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}