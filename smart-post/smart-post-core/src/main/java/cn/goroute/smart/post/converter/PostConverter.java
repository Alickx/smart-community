package cn.goroute.smart.post.converter;

import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.post.model.dto.PostAbbreviationDTO;
import cn.goroute.smart.post.model.dto.PostInfoDTO;
import cn.goroute.smart.post.model.dto.PostViewRankDTO;
import cn.goroute.smart.post.model.vo.PostVO;
import cn.goroute.smart.search.model.index.PostIndex;
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
	 * @return PostInfoDTO 文章DTO
	 */
	PostInfoDTO poToDto(Post post);

	/**
	 * PO 转 DTO
	 * @param post 文章
	 * @return PostAbbreviationDTO 文章缩略DTO
	 */
	PostAbbreviationDTO poToAbbreviationDto(Post post);

	/**
	 * VO 转 PO
	 * @param postVO 文章视图对象
	 * @return Post 实体类
	 */
	Post voToPo(PostVO postVO);

	PostIndex poToPostIndex(Post postEntity);

	@Mapping(target = "postId", source = "post.id")
	PostViewRankDTO poToViewRankDto(Post post);
}
