package cn.goroute.smart.common.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 点赞表
 * @TableName t_thumb
 */
@TableName(value ="t_thumb")
@Data
public class Thumb implements Serializable {
    /**
     * 主键uid
     */
    @TableId(type = IdType.AUTO)
    private Integer uid;

    /**
     * 点赞用户uid
     */
    private String memberUid;

    /**
     * 点赞目标的uid
     */
    private String toMemberUid;

    /**
     * 点赞内容的uid 评论uid或是文章uid
     */
    private String toUid;


    /**
     * 点赞所在的帖子uid
     */
    private String postUid;

    /**
     * 点赞类型 0 = 评论 1 = 文章
     */
    private Integer type;

    /**
     * 逻辑删除状态 0 = 正常 1 = 已删除
     */
    private Integer status;

    /**
     * 点赞时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}