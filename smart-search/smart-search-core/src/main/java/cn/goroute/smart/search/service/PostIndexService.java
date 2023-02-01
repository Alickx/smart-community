package cn.goroute.smart.search.service;

import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.post.model.dto.PostAbbreviationDTO;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.ballcat.common.model.result.R;

/**
 * @Author: Alickx
 * @Date: 2023/02/01/14:15
 * @Description:
 */
public interface PostIndexService {
	R<Boolean> save(Post post);

	R<PageResult<PostAbbreviationDTO>> pageByKeyword(PageParam pageParam, String keyword);
}
