package cn.goroute.smart.comment.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 文章回复表
 * 
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:34:33
 */
@Data
@TableName("t_comment")
public class CommentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键uid
	 */
	@TableId
	private String uid;
	/**
	 * 用户uid
	 */
	private String memberUid;
	/**
	 * 回复某条评论的uid
	 */
	private String toUid;
	/**
	 * 回复某个人的uid
	 */
	private String toMemberUid;
	/**
	 * 评论内容
	 */
	private String content;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 评论类型 0 = 评论 1 = 点赞
	 */
	private Integer type;
	/**
	 * 一级评论uid
	 */
	private String firstCommentUid;
	/**
	 * 
	 */
	private Date createdTime;

}
