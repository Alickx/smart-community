package cn.goroute.smart.post.domain.vo;


import cn.goroute.smart.post.domain.dto.ContentExpansionDTO;
import cn.goroute.smart.user.domain.vo.UserProfileVO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Alickx
 * @Date: 2022/10/28/0:08
 * @Description: 文章基础信息对象
 */
@Data
public class PostBaseVO {

	/**
	 * 文章id
	 */
	private Long id;

	/**
	 * 板块id
	 */
	private String categoryName;

	/**
	 * 标签id
	 */
	private String tagName;

	/**
	 * 文章作者id
	 */
	private Long authorId;

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
	 * 浏览数量
	 */
	private Integer viewCount;

	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 文章作者信息
	 */
	private UserProfileVO author;

	/**
	 * 板块信息
	 */
	private CategoryVO category;

	/**
	 * 标签信息
	 */
	private TagVO tag;

	/**
	 * 文章扩展信息
	 */
	private ContentExpansionDTO expansion;


	/**
	 * 文章作者发布时的地址
	 */
	private String region;


}
