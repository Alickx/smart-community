package cn.goroute.smart.post.modules.article.service;

import cn.goroute.smart.common.domain.SelectData;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.post.domain.entity.CategoryEntity;
import cn.goroute.smart.post.domain.entity.TagEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Alickx
* @description 针对表【category(板块表)】的数据库操作Service
* @createDate 2022-09-25 16:53:24
*/
public interface CategoryService extends IService<CategoryEntity> {

	/**
	 * 获取板块下拉数据及其标签数据
	 * @return 板块下拉数据及其标签数据
	 */
    R<List<SelectData<List<SelectData<TagEntity>>>>> getSelectData();


	CategoryEntity queryByName(String categoryName);


	List<CategoryEntity> getCategoryList();

	CategoryEntity queryById(Long categoryId);
}
