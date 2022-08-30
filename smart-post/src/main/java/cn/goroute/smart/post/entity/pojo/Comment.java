package cn.goroute.smart.post.entity.pojo;

import cn.goroute.smart.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 文章回复表
 * @author Alickx
 * @TableName t_comment
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_comment")
@Data
public class Comment extends BaseEntity implements Serializable {

    /**
     * 用户uid
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "用户uid不能为空")
    private Long memberId;

    /**
     * 文章uid
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "文章uid不能为空")
    private Long postId;

    /**
     * 回复某个人的uid
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "uid不能为空")
    private Long toMemberId;

    /**
     * 评论内容
     */
    @NotBlank(message = "评论内容不能为空")
    private String content;

    /**
     * 逻辑删除状态 0:未删除 1:已删除
     */
    private Integer status;

    /**
     * 0 = 一级评论 1 = 评论中回复
     */
    @NotNull(message = "评论类型不能为空")
    private Integer type;

    /**
     * 一级评论uid
     */
    @NotNull(message = "一级评论uid不能为空")
    private Long firstCommentId;
}