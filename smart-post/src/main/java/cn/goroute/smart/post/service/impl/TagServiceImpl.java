package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.post.domain.Tag;
import cn.goroute.smart.post.mapper.TagMapper;
import cn.goroute.smart.post.service.TagService;
import com.hccake.ballcat.common.model.domain.SelectData;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Alickx
* @description 针对表【tag(标签表)】的数据库操作Service实现
* @createDate 2022-09-25 16:53:24
*/
@Service
public class TagServiceImpl extends ExtendServiceImpl<TagMapper, Tag>
    implements TagService{

	/**
	 * 获取标签下拉数据
	 *
	 * @return 所有板块对应标签的下拉数据
	 */
	@Override
	public R<List<SelectData<Tag>>> getSelectData() {
		return null;
	}
}




