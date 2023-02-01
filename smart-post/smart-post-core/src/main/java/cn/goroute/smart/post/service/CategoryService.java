package cn.goroute.smart.post.service;

import cn.goroute.smart.post.domain.Category;
import cn.goroute.smart.post.domain.Tag;
import com.hccake.ballcat.common.model.domain.SelectData;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.extend.mybatis.plus.service.ExtendService;

import java.util.List;

/**
* @author Alickx
* @description 针对表【category(板块表)】的数据库操作Service
* @createDate 2022-09-25 16:53:24
*/
public interface CategoryService extends ExtendService<Category> {

	/**
	 * 获取板块下拉数据及其标签数据
	 * @return 板块下拉数据及其标签数据
	 */
    R<List<SelectData<List<SelectData<Tag>>>>> getSelectData();
}
