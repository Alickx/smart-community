package cn.goroute.smart.search.mapper;

import cn.goroute.smart.search.model.index.PostIndex;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.HighlightParameters;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Alickx
 * @Date: 2023/02/01/14:12
 * @Description:
 */
@Repository
@SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
public interface PostIndexMapper extends ElasticsearchRepository<PostIndex,Long> {

	/**
	 * 通过描述内容查询文章
	 * @param descriptiveContent 描述内容
	 * @param pageable 分页参数
	 * @return 搜索结果
	 */
	@Highlight(fields = {
			@HighlightField(name = PostIndex.Fields.title, parameters = @HighlightParameters(requireFieldMatch = false)),
			@HighlightField(name = PostIndex.Fields.summary, parameters = @HighlightParameters(requireFieldMatch = false)),
	})
	SearchPage<PostIndex> findByDescriptiveContentAndDeletedOrderByCreateTimeDesc(String descriptiveContent, Integer deleted, Pageable pageable);

}
