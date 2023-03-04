package cn.goroute.smart.post.modules.article.converter;

import cn.goroute.smart.post.domain.PostExpandInfoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Map;

/**
 * @Author: Alickx
 * @Date: 2023/03/04/11:36
 * @Description:
 */
@Mapper
public interface PostExpandInfoConverter {

	PostExpandInfoConverter INSTANCE = Mappers.getMapper(PostExpandInfoConverter.class);

	PostExpandInfoEntity mapToVo(Map<Object,Object> map);

}
