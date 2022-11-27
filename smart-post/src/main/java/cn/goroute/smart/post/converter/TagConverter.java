package cn.goroute.smart.post.converter;

import cn.goroute.smart.post.domain.Tag;
import cn.goroute.smart.post.model.dto.TagDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/09/27/14:09
 * @Description: 标签转换
 */
@Mapper
public interface TagConverter {

	TagConverter INSTANCE = Mappers.getMapper(TagConverter.class);

	/**
	 * 批量 po 转 dto
	 * @param tag 标签
	 * @return 标签dto
	 */
	List<TagDTO> poToDTO(List<Tag> tag);

	/**
	 * po 转 dto
	 * @param tag 标签
	 * @return 标签dto
	 */
	@Mapping(target = "tagId", source = "id")
	TagDTO poToDTO(Tag tag);

}
