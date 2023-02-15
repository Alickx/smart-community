package cn.goroute.smart.post.domain.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;

import java.time.LocalDateTime;

/**
 * @author: Alickx
 * @Date: 2023/02/14 21:05:42
 * @Description:
 */
@Data
@Schema(name = "评论DTO对象")
@ParameterObject
public class CommentDTO {


	/**
	 * 主键id
	 */
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
	private Integer deleted;



}
