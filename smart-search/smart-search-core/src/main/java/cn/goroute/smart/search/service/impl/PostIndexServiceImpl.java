package cn.goroute.smart.search.service.impl;

import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.post.model.dto.PostAbbreviationDTO;
import cn.goroute.smart.search.converter.PostIndexConverter;
import cn.goroute.smart.search.manager.PostIndexManagerService;
import cn.goroute.smart.search.mapper.PostIndexMapper;
import cn.goroute.smart.search.model.index.PostIndex;
import cn.goroute.smart.search.service.PostIndexService;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.ballcat.common.model.result.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	private final PostIndexManagerService postIndexManagerService;
	private final RestHighLevelClient restHighLevelClient;

	@Override
	public R<Boolean> save(Post post) {
		PostIndex postIndex = PostIndexConverter.INSTANCE.poToIndex(post);
		postIndexMapper.save(postIndex);
		return R.ok(true);
	}

	@Override
	public R<PageResult<PostAbbreviationDTO>> pageByKeyword(PageParam pageParam, String keyword) {

		SearchPage<PostIndex> searchPage = postIndexMapper.
				findByDescriptiveContent(keyword, PageRequest.of((int) pageParam.getPage() - 1, (int) pageParam.getSize()));

		List<SearchHit<PostIndex>> searchHitList = searchPage.getContent();
		List<PostIndex> postIndexList = new ArrayList<>(searchHitList.size());

		for (SearchHit<PostIndex> postHit : searchHitList) {
			PostIndex postIndex = postHit.getContent();
			// 获取高亮数据
			Map<String, List<String>> fields = postHit.getHighlightFields();
			if (fields.size() > 0) {
				BeanMap beanMap = BeanMap.create(postIndex);
				for (String name : fields.keySet()) {
					beanMap.put(name, fields.get(name).get(0));
				}
			}
			postIndexList.add(postIndex);
		}

		log.info("postIndexList: {}", postIndexList);

		return R.ok(null);

	}
}
