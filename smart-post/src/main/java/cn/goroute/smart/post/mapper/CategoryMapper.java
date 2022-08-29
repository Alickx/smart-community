package cn.goroute.smart.post.mapper;

import cn.goroute.smart.post.entity.pojo.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 板块表
 * 
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:44:39
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
	
}
