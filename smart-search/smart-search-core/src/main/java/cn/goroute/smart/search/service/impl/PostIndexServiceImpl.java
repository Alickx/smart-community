package cn.goroute.smart.search.service.impl;

import cn.goroute.smart.post.domain.entity.PostEntity;
import cn.goroute.smart.search.converter.PostIndexConverter;
import cn.goroute.smart.search.manager.PostIndexManagerService;
import cn.goroute.smart.search.mapper.PostIndexMapper;
import cn.goroute.smart.search.model.dto.PostIndexDTO;
import cn.goroute.smart.search.model.index.PostIndex;
import cn.goroute.smart.search.service.PostIndexService;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.ballcat.common.model.result.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Service;

import java.util.List;

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


	@Override
	public R<Boolean> save(PostIndex postIndex) {
		postIndexMapper.save(postIndex);
		return R.ok(true);
	}

	@Override
	public R<PageResult<PostIndexDTO>> pageByKeyword(PageParam pageParam, String keyword) {

		SearchPage<PostIndex> searchPage = postIndexMapper.
				findByDescriptiveContentOrderByCreateTimeDesc(keyword, PageRequest.of((int) pageParam.getPage() - 1,
						(int) pageParam.getSize()));

		List<SearchHit<PostIndex>> searchHitList = searchPage.getContent();

		// 从ES的分页搜索结果中获取实体类集合
		List<PostIndex> postIndexList = postIndexManagerService.getPostIndexList(searchHitList);

		// 填充信息
		PageResult<PostIndexDTO> result = postIndexManagerService.fillAuthorInfo(postIndexList,searchPage);


		return R.ok(result);

	}

	@Override
	public void postSync(PostEntity postEntity) {


		PostIndex postIndex = PostIndexConverter.INSTANCE.postToPostIndex(postEntity);

		// 不存在则新增
		postIndexMapper.save(postIndex);

	}
}
