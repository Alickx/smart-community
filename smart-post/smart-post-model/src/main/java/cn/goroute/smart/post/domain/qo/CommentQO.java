package cn.goroute.smart.post.domain.qo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: Alickx
 * @Date: 2022/11/25/0:25
 * @Description: 文章评论查询对象
 */
@Data
public class CommentQO {

	/**
	 * 发送方id
	 */
	private Long userId;

	/**
	 * 文章id
	 */
	private Long postId;

	/**
	 * 回复类型 0 = 一级评论 1 = 评论中回复
	 */
	@NotNull(message = "回复类型不能为空")
	private Integer type;

	/**
	 * 一级评论uid
	 */
	private Long firstCommentId;

}
