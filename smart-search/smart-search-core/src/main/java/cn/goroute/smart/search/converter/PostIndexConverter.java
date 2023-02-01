package cn.goroute.smart.search.converter;

import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.search.model.index.PostIndex;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author: Alickx
 * @Date: 2023/02/01/14:37
 * @Description:
 */
@Mapper
public interface PostIndexConverter {

	PostIndexConverter INSTANCE = Mappers.getMapper(PostIndexConverter.class);


	PostIndex poToIndex(Post post);
}
