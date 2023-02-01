package cn.goroute.smart.search.mapper;

import cn.goroute.smart.search.model.index.PostIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Alickx
 * @Date: 2023/02/01/14:12
 * @Description:
 */
@Repository
public interface PostIndexMapper extends ElasticsearchRepository<PostIndex,Long> {


}
