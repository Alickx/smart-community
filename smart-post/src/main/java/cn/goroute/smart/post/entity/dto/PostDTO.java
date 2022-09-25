package cn.goroute.smart.post.entity.dto;

import cn.goroute.smart.common.entity.dto.UserProfileDto;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @Author: Alickx
 * @Date: 2022/09/25/18:12
 * @Description: 文章信息对象
 */
@Data
@ToString
public class PostDTO {

	/**
	 * 文章id
	 */
	private Long id;

	/**
	 * 板块信息
	 */
	private CategoryDTO category;

	/**
	 * 文章作者信息
	 */
	private UserProfileDto author;

	/**
	 * 文章题目
	 */
	private String title;

	/**
	 * 文章内容
	 */
	private String content;

	/**
	 * 收藏数量
	 */
	private Integer collectCount;

	/**
	 * 点赞数量
	 */
	private Integer thumbCount;

	/**
	 * 评论数量
	 */
	private Integer commentCount;

	/**
	 * 文章摘要
	 */
	private String summary;

	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

}
