package cn.goroute.smart.post.converter.impl;

import cn.goroute.smart.post.converter.PostConverter;
import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.post.entity.dto.PostAbbreviationDTO;
import cn.goroute.smart.post.entity.dto.PostDTO;
import cn.goroute.smart.post.util.Html2TextUtil;
import lombok.RequiredArgsConstructor;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/10/21/16:35
 * @Description: 自定义映射
 */
@RequiredArgsConstructor
public class PostConvertDecorator implements PostConverter {

	private final PostConverter postConverter;

	/**
	 * PO 转 DTO
	 *
	 * @param post 文章
	 * @return PostDTO 文章DTO
	 */
	@Override
	public PostDTO poToDto(Post post) {
		return postConverter.poToDto(post);
	}

	/**
	 * PO 转 DTO
	 *
	 * @param post 文章
	 * @return PostAbbreviationDTO 文章缩略DTO
	 */
	@Override
	public PostAbbreviationDTO poToAbbreviationDto(Post post) {
		PostAbbreviationDTO postAbbreviationDTO = postConverter.poToAbbreviationDto(post);
		String content = Html2TextUtil.Html2Text(post.getContent());
		// 截取文章内容前100个字符
		postAbbreviationDTO.setSummary(content.substring(0, Math.min(content.length(), 250)));
		return postAbbreviationDTO;
	}
}
