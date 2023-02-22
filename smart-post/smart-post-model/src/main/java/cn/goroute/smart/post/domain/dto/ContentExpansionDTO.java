package cn.goroute.smart.post.domain.dto;

import lombok.Data;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/09/27/14:22
 * @Description: 文章扩展对象
 */
@Data
public class ContentExpansionDTO {

	/**
	 * 是否评论
	 */
	private Boolean isComment;

	/**
	 * 是否收藏
	 */
	private Boolean isCollect;

	/**
	 * 是否点赞
	 */
	private Boolean isThumb;

	/**
	 * 是否是作者
	 */
	private Boolean isAuthor;

	/**
	 * 是否有更多二级回复
	 */
	private Boolean isMoreReply;

	public void init() {
		this.isComment = false;
		this.isCollect = false;
		this.isThumb = false;
		this.isAuthor = false;
		this.isMoreReply = false;
	}

	public static ContentExpansionDTO create() {
		ContentExpansionDTO contentExpansionDTO = new ContentExpansionDTO();
		contentExpansionDTO.init();
		return contentExpansionDTO;
	}

}
