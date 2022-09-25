package cn.goroute.smart.post.entity.dto;

import cn.goroute.smart.common.entity.dto.UserProfileDto;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @Author: Alickx
 * @Date: 2022/09/25/18:13
 * @Description: 文章缩略信息对象
 */
@Data
@ToString
public class PostAbbreviationDTO {

	/**
	 * 文章id
	 */
	private Long id;

	/**
	 * 文章作者信息
	 */
	private UserProfileDto author;

	/**
	 * 板块信息
	 */
	private CategoryDTO category;

	/**
	 * 文章题目
	 */
	private String title;

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
	 * 创建时间
	 */
	private LocalDateTime createTime;

}
