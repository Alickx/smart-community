package cn.goroute.smart.post.modules.article.converter;

import cn.goroute.smart.post.domain.dto.PostViewRankDTO;
import cn.goroute.smart.post.domain.entity.PostEntity;
import cn.goroute.smart.post.domain.vo.PostAbbreviationVO;
import cn.goroute.smart.post.domain.vo.PostInfoVO;
import cn.goroute.smart.post.domain.vo.PostVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

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
	 * @param postEntity 文章
	 * @return PostInfoDTO 文章DTO
	 */
	PostInfoVO poToDto(PostEntity postEntity);

	/**
	 * PO 转 DTO
	 * @param postEntity 文章
	 * @return PostAbbreviationDTO 文章缩略DTO
	 */
	PostAbbreviationVO poToAbbreviationDto(PostEntity postEntity);

	/**
	 * VO 转 PO
	 * @param postVO 文章视图对象
	 * @return Post 实体类
	 */
	PostEntity voToPo(PostVO postVO);


	@Mapping(target = "postId", source = "postEntity.id")
	PostViewRankDTO poToViewRankDto(PostEntity postEntity);


	List<PostAbbreviationVO> poToAbbreviationDto(List<PostEntity> postEntityList);
}
