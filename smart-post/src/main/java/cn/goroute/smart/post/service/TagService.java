package cn.goroute.smart.post.service;

import cn.goroute.smart.post.domain.Tag;
import com.hccake.ballcat.common.model.domain.SelectData;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.extend.mybatis.plus.service.ExtendService;

import java.util.List;

/**
* @author Alickx
* @description 针对表【tag(标签表)】的数据库操作Service
* @createDate 2022-09-25 16:53:24
*/
public interface TagService extends ExtendService<Tag> {

	/**
	 * 获取标签下拉数据
	 * @return 所有板块对应标签的下拉数据
	 */
    R<List<SelectData<Tag>>> getSelectData();
}
