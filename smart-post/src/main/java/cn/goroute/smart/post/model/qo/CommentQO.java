package cn.goroute.smart.post.model.qo;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;

import javax.validation.constraints.NotNull;

/**
 * @Author: Alickx
 * @Date: 2022/11/25/0:25
 * @Description: 文章评论查询对象
 */
@Data
@Schema(name = "评论查询对象")
@ParameterObject
public class CommentQO {

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
	 * 回复类型 0 = 一级评论 1 = 评论中回复
	 */
	@Parameter(description = "回复类型 0 = 一级评论 1 = 评论中回复")
	@NotNull(message = "回复类型不能为空")
	private Integer type;

	/**
	 * 一级评论uid
	 */
	@Parameter(description = "一级评论uid")
	private Long firstCommentId;

}
