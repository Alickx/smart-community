package cn.goroute.smart.post.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文章回复表
 * @TableName comment
 */
@TableName(value ="comment")
@Data
@ToString
public class CommentEntity implements Serializable {
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
    private Integer status;

    /**
     * 回复类型 0 = 一级评论 1 = 评论中回复
     */
    private Integer type;

    /**
     * 一级评论uid
     */
    private Long firstCommentId;

	/**
	 * 点赞数量
	 */
	private Integer thumbCount;

    /**
     * 发布评论时的ip
     */
    private String ip;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标记 0 = 正常 1 = 删除
     */
    @TableLogic
    private Integer deleted;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}