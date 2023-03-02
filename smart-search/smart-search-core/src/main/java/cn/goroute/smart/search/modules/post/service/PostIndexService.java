package cn.goroute.smart.search.modules.post.service;

import cn.goroute.smart.common.domain.PageParam;
import cn.goroute.smart.common.domain.PageResult;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.post.domain.entity.PostEntity;
import cn.goroute.smart.search.model.dto.PostIndexDTO;
import cn.goroute.smart.search.model.index.PostIndex;

/**
 * @Author: Alickx
 * @Date: 2023/02/01/14:15
 * @Description:
 */
public interface PostIndexService {
	R<Boolean> save(PostIndex postIndex);

	R<PageResult<PostIndexDTO>> pageByKeyword(PageParam pageParam, String keyword);

	void postSync(PostEntity postEntity);
}
