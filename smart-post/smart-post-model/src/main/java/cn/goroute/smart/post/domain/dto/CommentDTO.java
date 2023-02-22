package cn.goroute.smart.post.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: Alickx
 * @Date: 2023/02/14 21:05:42
 * @Description:
 */
@Data
public class CommentDTO {


	/**
	 * 主键id
	 */
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
	 * 文章标题
	 */
	private String postTitle;

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
	private Integer state;

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
	private Integer deleted;



}
