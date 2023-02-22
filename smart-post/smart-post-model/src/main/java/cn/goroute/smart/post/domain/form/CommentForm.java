package cn.goroute.smart.post.domain.form;

import lombok.Data;

/**
 * @Author: Alickx
 * @Date: 2022/11/25/0:26
 * @Description: 文章评论视图对象
 */
@Data
public class CommentForm {

	/**
	 * 主键id
	 */
	private Long id;

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
	 * 回复类型 0 = 一级评论 1 = 评论中回复
	 */
	private Integer type;

	/**
	 * 一级评论uid
	 */
	private Long firstCommentId;

}
