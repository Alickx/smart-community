package cn.goroute.smart.search.service;

import cn.goroute.smart.common.exception.ServiceException;
import cn.goroute.smart.common.utils.R;
import cn.goroute.smart.search.config.EsConstant;
import cn.goroute.smart.search.model.PostEsModel;
import cn.goroute.smart.search.model.SearchParam;
import cn.goroute.smart.search.model.PostSearchResponse;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static cn.goroute.smart.common.utils.Constant.POST_ES_INDEX;

@Service
@Slf4j
public class PostSearchService {

    @Autowired
    RestHighLevelClient restHighLevelClient;


    public String save(PostEsModel postEsModel) throws IOException {
        log.info("=>存储文章数据到es中");
        IndexRequest indexRequest = new IndexRequest(POST_ES_INDEX);
        indexRequest.id(postEsModel.getUid());
        String s = JSON.toJSONString(postEsModel);
        indexRequest.source(s, XContentType.JSON);
        IndexResponse response = restHighLevelClient
                .index(indexRequest, RequestOptions.DEFAULT);
        log.info("=>存储文章结束，存储响应=>{}", response);
        return response.toString();
    }


    public R search(SearchParam searchParam) {

        //构建DSL语句
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (!StrUtil.isEmpty(searchParam.getKeyword())) {
            boolQuery.must(QueryBuilders.multiMatchQuery(searchParam.getKeyword(), "title", "content"));
        } else {
            return R.error("搜索字段不存在");
        }
        searchSourceBuilder.query(boolQuery);

        //分页
        searchSourceBuilder.from((searchParam.getPageNum() - 1) * EsConstant.pageSize);
        searchSourceBuilder.size(EsConstant.pageSize);

        SearchRequest searchRequest = new SearchRequest(new String[]{EsConstant.POST_INDEX}, searchSourceBuilder);

        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            //获取查询到的结果
            SearchHits hits = searchResponse.getHits();
            //获取命中的结果
            SearchHit[] searchHits = hits.getHits();

            List<PostEsModel> postEsModelList = new ArrayList<>();
            if (hits.getHits() != null && hits.getHits().length > 0) {
                for (SearchHit hit : searchHits) {
                    String sourceAsString = hit.getSourceAsString();
                    PostEsModel postEsModel = JSONObject.parseObject(sourceAsString, PostEsModel.class);
                    postEsModelList.add(postEsModel);
                }
            }
            PostSearchResponse postSearchResponse = new PostSearchResponse();
            postSearchResponse.setPostList(postEsModelList);

            //分页参数
            long total = hits.getTotalHits().value;
            postSearchResponse.setTotal(total);
            postSearchResponse.setPageNum(searchParam.getPageNum());
            int totalPages = (int) (total % EsConstant.pageSize) == 0 ? (int) (total / EsConstant.pageSize) : (int) (total / EsConstant.pageSize + 1);
            postSearchResponse.setTotalPages(totalPages);
            return R.ok().put("data", postSearchResponse);

        } catch (IOException e) {
            throw new ServiceException(ExceptionUtil.getMessage(e));
        }
    }
}
