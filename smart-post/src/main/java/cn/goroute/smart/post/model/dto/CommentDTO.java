package cn.goroute.smart.post.model.dto;

import cn.goroute.smart.common.model.dto.UserProfileDTO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.hccake.ballcat.common.model.domain.PageResult;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Alickx
 * @Date: 2022/11/25/0:25
 * @Description: 文章评论数据传输对象
 */
@Data
@Schema(name = "文章评论数据传输对象")
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
	 * 创建时间
	 */
	@Parameter(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 用户信息
	 */
	@Parameter(description = "用户信息")
	private UserProfileDTO userProfile;

	/**
	 * 点赞数量
	 */
	@Parameter(description = "点赞数量")
	private Integer thumbCount;

	/**
	 * 拓展内容
	 */
	@Parameter(description = "拓展内容")
	private ContentExpansionDTO expansion;

	/**
	 * 二级回复
	 */
	@Parameter(description = "二级回复")
	private PageResult<CommentDTO> replyList;

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

}
