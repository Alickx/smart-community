package cn.goroute.smart.post.converter;

import cn.goroute.smart.post.domain.Category;
import cn.goroute.smart.post.entity.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/09/27/12:05
 * @Description: 板块转换
 */
@Mapper
public interface CategoryConverter {
	
	CategoryConverter INSTANCE = Mappers.getMapper(CategoryConverter.class);

	/**
	 * po 转 dto
	 * @param category 板块
	 * @return 板块dto
	 */
	@Mapping(target = "categoryId",source = "id")
	CategoryDTO poToDTO(Category category);

	/**
	 * 批量 po 转 dto
	 * @param category 板块
	 * @return 板块dto
	 */
	List<CategoryDTO> poToDTO(List<Category> category);
	
}
