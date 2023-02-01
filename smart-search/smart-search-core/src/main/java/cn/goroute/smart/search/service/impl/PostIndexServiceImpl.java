package cn.goroute.smart.search.service.impl;

import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.post.model.dto.PostAbbreviationDTO;
import cn.goroute.smart.search.converter.PostIndexConverter;
import cn.goroute.smart.search.manage.PostIndexManageService;
import cn.goroute.smart.search.mapper.PostIndexMapper;
import cn.goroute.smart.search.model.index.PostIndex;
import cn.goroute.smart.search.service.PostIndexService;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.ballcat.common.model.result.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author: Alickx
 * @Date: 2023/02/01/14:15
 * @Description:
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PostIndexServiceImpl implements PostIndexService {

	private final PostIndexMapper postIndexMapper;

	private final PostIndexManageService postIndexManageService;
	private final RestHighLevelClient restHighLevelClient;

	@Override
	public R<Boolean> save(Post post) {
		PostIndex postIndex = PostIndexConverter.INSTANCE.poToIndex(post);
		postIndexMapper.save(postIndex);
		return R.ok(true);
	}

	@Override
	public R<PageResult<PostAbbreviationDTO>> pageByKeyword(PageParam pageParam, String keyword) {

		SearchRequest searchRequest = postIndexManageService.buildKwPageSearchRequest(pageParam, keyword);
		try {
			SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
			return R.ok(postIndexManageService.buildPageResult(searchResponse));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
