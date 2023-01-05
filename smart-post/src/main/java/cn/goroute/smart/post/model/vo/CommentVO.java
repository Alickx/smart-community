package cn.goroute.smart.post.model.vo;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;

/**
 * @Author: Alickx
 * @Date: 2022/11/25/0:26
 * @Description: 文章评论视图对象
 */
@Data
@Schema(name = "评论视图对象")
@ParameterObject
public class CommentVO {

	/**
	 * 主键id
	 */
	@Parameter(description = "主键id")
	private Long id;

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
	 * 回复类型 0 = 一级评论 1 = 评论中回复
	 */
	@Parameter(description = "回复类型 0 = 一级评论 1 = 评论中回复")
	private Integer type;

	/**
	 * 一级评论uid
	 */
	@Parameter(description = "一级评论uid")
	private Long firstCommentId;

}
