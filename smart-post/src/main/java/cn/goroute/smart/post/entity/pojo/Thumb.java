package cn.goroute.smart.post.entity.pojo;

import cn.goroute.smart.common.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 点赞表
 * @author Alickx
 * @TableName t_thumb
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_thumb")
@Data
public class Thumb extends BaseEntity implements Serializable {

    /**
     * 点赞用户uid
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long memberUid;

    /**
     * 点赞目标的uid
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long toMemberUid;

    /**
     * 点赞内容的uid 评论uid或是文章uid
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long toUid;


    /**
     * 点赞所在的帖子uid
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long postUid;

    /**
     * 点赞类型 0 = 评论 1 = 文章
     */
    private Integer type;

    /**
     * 点赞状态 0 = 取消 1 = 点赞
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}