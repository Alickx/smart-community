package cn.goroute.smart.post.mapper;

import cn.goroute.smart.post.entity.pojo.CategoryTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Alickx
* @description 针对表【t_section_tag(分类标签关联表)】的数据库操作Mapper
* @createDate 2022-03-04 16:06:54
* @Entity cn.goroute.smart.common.entity.pojo.SectionTag
*/
@Mapper
public interface CategoryTagMapper extends BaseMapper<CategoryTag> {

}




