package cn.goroute.smart.post.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Alickx
 * @Date: 2023/01/07/11:34
 * @Description:
 */
@Data
public class ThumbDTO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 点赞用户id
     */
    private Long userId;

    /**
     * 点赞内容的id
     */
    private Long toId;

	/**
	 * 被点赞对象所属用户id
	 */
	private Long toUserId;

	/**
	 * 点赞内容 评论内容 或是 文章标题等
	 */
	private String content;

    /**
     * 点赞类型 0 = 文章 1 = 评论 2 = 回复
     */
    private Integer type;

	/**
	 * 文章id
	 */
	private Long postId;

    /**
     * 文章标题
     */
    private String postTitle;

    /**
     * 逻辑删除 1 = 已取消点赞
     */
    private Integer deleted;

    /**
     * 点赞时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否是保存点赞 true = 点赞 false = 取消点赞
     */
    private Boolean saveFlag;

}
