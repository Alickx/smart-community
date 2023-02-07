package cn.goroute.smart.search.service;

import cn.goroute.smart.search.model.dto.PostIndexDTO;
import cn.goroute.smart.search.model.index.PostIndex;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.ballcat.common.model.result.R;

/**
 * @Author: Alickx
 * @Date: 2023/02/01/14:15
 * @Description:
 */
public interface PostIndexService {
	R<Boolean> save(PostIndex postIndex);

	R<PageResult<PostIndexDTO>> pageByKeyword(PageParam pageParam, String keyword);

	void postSync(PostIndex postIndex);
}
