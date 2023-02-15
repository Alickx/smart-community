package cn.goroute.smart.post.domain.dto;


import cn.goroute.smart.user.model.dto.UserProfileDTO;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Alickx
 * @Date: 2022/10/28/0:08
 * @Description: 文章基础信息对象
 */
@Data
public class PostBaseDTO {

	/**
	 * 文章id
	 */
	@Parameter(description = "文章id")
	private Long id;

	/**
	 * 板块id
	 */
	@Parameter(description = "板块id")
	private String categoryName;

	/**
	 * 标签id
	 */
	@Parameter(description = "标签名称")
	private String tagName;

	/**
	 * 文章作者id
	 */
	@Parameter(description = "文章作者id")
	private Long authorId;

	/**
	 * 文章题目
	 */
	@Parameter(description = "文章题目")
	private String title;

	/**
	 * 收藏数量
	 */
	@Parameter(description = "收藏数量")
	private Integer collectCount;

	/**
	 * 点赞数量
	 */
	@Parameter(description = "点赞数量")
	private Integer thumbCount;

	/**
	 * 评论数量
	 */
	@Parameter(description = "评论数量")
	private Integer commentCount;

	/**
	 * 浏览数量
	 */
	@Parameter(description = "浏览数量")
	private Integer viewCount;

	/**
	 * 更新时间
	 */
	@Parameter(description = "更新时间")
	private LocalDateTime updateTime;

	/**
	 * 创建时间
	 */
	@Parameter(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 文章作者信息
	 */
	@Parameter(description = "文章作者信息")
	private UserProfileDTO author;

	/**
	 * 板块信息
	 */
	@Parameter(description = "板块信息")
	private CategoryDTO category;

	/**
	 * 标签信息
	 */
	@Parameter(description = "标签信息")
	private TagDTO tag;

	/**
	 * 文章扩展信息
	 */
	@Parameter(description = "文章扩展信息")
	private ContentExpansionDTO expansion;


	/**
	 * 文章作者发布时的地址
	 */
	@Parameter(description = "文章作者发布时的地址")
	private String region;


}
