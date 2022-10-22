package cn.goroute.smart.post.entity.dto;

import cn.goroute.smart.common.entity.dto.UserProfileDTO;
import cn.goroute.smart.post.entity.bo.PostExpansionBO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import org.springdoc.api.annotations.ParameterObject;

import java.time.LocalDateTime;

/**
 * @Author: Alickx
 * @Date: 2022/09/25/18:13
 * @Description: 文章缩略信息对象
 */
@Data
@ToString
@Schema(name = "文章缩略DTO对象")
@ParameterObject
public class PostAbbreviationDTO {


	/**
	 * 文章id
	 */
	@Parameter(description = "文章id")
	private Long id;

	/**
	 * 板块id
	 */
	@Parameter(description = "板块id")
	private Long categoryId;

	/**
	 * 标签id
	 */
	@Parameter(description = "标签id")
	private Long tagId;

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
	 * 文章摘要
	 */
	@Parameter(description = "文章摘要")
	private String summary;

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
	private PostExpansionBO expansion;

}
