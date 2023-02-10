package cn.goroute.smart.post.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import org.springdoc.api.annotations.ParameterObject;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文章回复表
 * @TableName comment
 */
@TableName(value ="comment")
@Data
@Schema(name = "文章回复表")
@ParameterObject
@ToString
public class Comment implements Serializable {
    /**
     * 主键id
     */
    @TableId
	@Parameter(description = "主键id")
    private Long id;

    /**
     * 发送方id
     */
	@Parameter(description = "发送方id")
    private Long userId;

    /**
     * 文章id
     */
	@Parameter(description = "文章id")
    private Long postId;

    /**
     * 接收方id
     */
	@Parameter(description = "接收方id")
    private Long toUserId;

    /**
     * 评论内容
     */
	@Parameter(description = "评论内容")
    private String content;

    /**
     * 状态 0 = 正常显示 1 = 隐藏
     */
	@Parameter(description = "状态 0 = 正常显示 1 = 隐藏")
    private Integer state;

    /**
     * 回复类型 0 = 一级评论 1 = 评论中回复
     */
	@Parameter(description = "回复类型 0 = 一级评论 1 = 评论中回复")
    private Integer type;

    /**
     * 一级评论uid
     */
	@Parameter(description = "一级评论uid")
    private Long firstCommentId;

	/**
	 * 点赞数量
	 */
	@Parameter(description = "点赞数量")
	private Integer thumbCount;

    /**
     * 创建时间
     */
	@Parameter(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 
     */
	@Parameter(description = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标记 0 = 正常 1 = 删除
     */
	@Parameter(description = "逻辑删除标记 0 = 正常 1 = 删除")
    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}