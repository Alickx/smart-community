package cn.goroute.smart.search.converter;


import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.search.model.dto.PostIndexDTO;
import cn.goroute.smart.search.model.index.PostIndex;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2023/02/01/14:37
 * @Description:
 */
@Mapper
public interface PostIndexConverter {

	PostIndexConverter INSTANCE = Mappers.getMapper(PostIndexConverter.class);

	PostIndexDTO indexToDTO(PostIndex postIndex);

	List<PostIndexDTO> indexToDTO(List<PostIndex> postIndex);

	PostIndex postToPostIndex(Post post);
}