package cn.goroute.smart.post.converter;

import cn.goroute.smart.post.converter.impl.PostConvertDecorator;
import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.post.entity.dto.PostAbbreviationDTO;
import cn.goroute.smart.post.entity.dto.PostDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @Author: Alickx
 * @Date: 2022/09/25/17:37
 * @Description: 文章
 */
@Mapper
@DecoratedWith(PostConvertDecorator.class)
public interface PostConverter {

	PostConverter INSTANCE = Mappers.getMapper(PostConverter.class);

	/**
	 * PO 转 DTO
	 * @param post 文章
	 * @return PostDTO 文章DTO
	 */
	PostDTO poToDto(Post post);

	/**
	 * PO 转 DTO
	 * @param post 文章
	 * @return PostAbbreviationDTO 文章缩略DTO
	 */
	PostAbbreviationDTO poToAbbreviationDto(Post post);
}
