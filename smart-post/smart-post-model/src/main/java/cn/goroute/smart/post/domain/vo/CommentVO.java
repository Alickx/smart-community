package cn.goroute.smart.post.domain.vo;

import cn.goroute.smart.common.domain.PageResult;
import cn.goroute.smart.post.domain.dto.ContentExpansionDTO;
import cn.goroute.smart.user.domain.vo.UserProfileVO;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Alickx
 * @Date: 2022/11/25/0:25
 * @Description: 文章评论数据传输对象
 */
@Data
public class CommentVO {

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

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 用户信息
	 */
	private UserProfileVO userProfile;

	/**
	 * 点赞数量
	 */
	private Integer thumbCount;

	/**
	 * 拓展内容
	 */
	private ContentExpansionDTO expansion;

	/**
	 * 二级回复
	 */
	private PageResult<CommentVO> replyList;

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

}
