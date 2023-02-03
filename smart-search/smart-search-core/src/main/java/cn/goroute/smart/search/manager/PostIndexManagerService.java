package cn.goroute.smart.search.manager;

import cn.goroute.smart.post.model.dto.PostAbbreviationDTO;
import cn.goroute.smart.search.model.index.PostIndex;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

/**
 * @Author: Alickx
 * @Date: 2023/02/01/15:13
 * @Description:
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PostIndexManagerService {

	/**
	 * 构建关键词分页查询请求参数
	 *
	 * @param pageParam 分页参数
	 * @param keyword   关键词
	 * @return SearchRequest
	 */
	public SearchRequest buildKwPageSearchRequest(PageParam pageParam, String keyword) {
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource()
				.from((int) pageParam.getPage())
				.size((int) pageParam.getSize())
				.query(QueryBuilders.matchQuery(PostIndex.Fields.title, keyword))
				.query(QueryBuilders.matchQuery(PostIndex.Fields.content, keyword));

		searchRequest.source(searchSourceBuilder);

		return searchRequest;
	}

	public PageResult<PostAbbreviationDTO> buildPageResult(SearchResponse searchResponse) {

		long total = searchResponse.getHits().getTotalHits().value;



		return null;
	}
}
