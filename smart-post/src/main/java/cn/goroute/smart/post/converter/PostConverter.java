package cn.goroute.smart.post.converter;

import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.post.entity.dto.PostAbbreviationDTO;
import cn.goroute.smart.post.entity.dto.PostDTO;
import cn.goroute.smart.post.entity.vo.PostVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @Author: Alickx
 * @Date: 2022/09/25/17:37
 * @Description: 文章
 */
@Mapper
public interface PostConverter {

	PostConverter INSTANCE = Mappers.getMapper(PostConverter.class);

	/**
	 * PO 转 DTO
	 * @param post 文章
	 * @return PostDTO 文章DTO
	 */
	@Mapping(target = "category", ignore = true)
	@Mapping(target = "author", ignore = true)
	PostDTO poToDto(Post post);

	/**
	 * PO 转 DTO
	 * @param post 文章
	 * @return PostAbbreviationDTO 文章缩略DTO
	 */
	@Mapping(target = "category", ignore = true)
	@Mapping(target = "author", ignore = true)
	PostAbbreviationDTO poToAbbreviationDto(Post post);

	/**
	 * VO 转 PO
	 * @param postVO 文章视图对象
	 * @return Post 文章
	 */
    Post voToPo(PostVO postVO);
}
