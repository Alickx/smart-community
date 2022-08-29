package cn.goroute.smart.search.service;

import cn.goroute.smart.common.entity.dto.PostListDto;
import cn.goroute.smart.common.entity.pojo.Post;
import cn.goroute.smart.common.exception.ServiceException;
import cn.goroute.smart.common.constant.PostConstant;
import cn.goroute.smart.common.constant.RedisKeyConstant;
import cn.goroute.smart.common.service.AuthService;
import cn.goroute.smart.common.utils.RedisUtil;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.search.constant.EsConstant;
import cn.goroute.smart.search.feign.MemberFeignService;
import cn.goroute.smart.search.feign.PostFeignService;
import cn.goroute.smart.search.model.PostEsModel;
import cn.goroute.smart.search.model.PostSearchParam;
import cn.goroute.smart.search.model.PostSearchResponse;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Alickx
 */
@Service
@Slf4j
public class PostSearchService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Resource
    MemberFeignService memberFeignService;

    @Resource
    PostFeignService postFeignService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    AuthService authService;

    private static final String POST_ES_INDEX = "smart-post";

    public String save(PostEsModel postEsModel) throws IOException {
        log.info("=>存储文章数据到es中");
        IndexRequest indexRequest = new IndexRequest(POST_ES_INDEX);
        indexRequest.id(String.valueOf(postEsModel.getUid()));
        String s = JSON.toJSONString(postEsModel);
        indexRequest.source(s, XContentType.JSON);
        IndexResponse response = restHighLevelClient
                .index(indexRequest, RequestOptions.DEFAULT);
        log.info("=>存储文章结束，存储响应=>{}", response);
        return response.toString();
    }


    /**
     * 查询服务-文章
     *
     * @param postSearchParam 查询分页参数
     *                        keyword: 关键字
     *                        curPage: 页数
     * @return 查询结果
     */
    public Result search(PostSearchParam postSearchParam) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (!CharSequenceUtil.isEmpty(postSearchParam.getKeyword())) {
            boolQuery.must(QueryBuilders.multiMatchQuery(postSearchParam.getKeyword(), "title", "summary", "content"));
        } else {
            return Result.error("搜索字段不存在");
        }
        boolQuery.filter(QueryBuilders.termQuery("status", 0));
        searchSourceBuilder.query(boolQuery);
        searchSourceBuilder.from((postSearchParam.getCurPage() - 1) * EsConstant.PAGE_SIZE);
        searchSourceBuilder.size(EsConstant.PAGE_SIZE);
        searchSourceBuilder.sort("createdTime", Integer.parseInt(postSearchParam.getSortType()) == 0 ? SortOrder.ASC : SortOrder.DESC);
        searchSourceBuilder.highlighter(new HighlightBuilder()
                .requireFieldMatch(false).field("title")
                .field("summary")
                .preTags("<span style='color:red;'>")
                .postTags("</span>"));
        SearchRequest searchRequest = new SearchRequest(new String[]{EsConstant.POST_INDEX}, searchSourceBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();

            List<PostEsModel> postEsModelList = new ArrayList<>();
            highlightHandle(hits, searchHits, postEsModelList);
            PostSearchResponse postSearchResponse = new PostSearchResponse();
            List<PostListDto> postListDtos = new ArrayList<>();
            for (PostEsModel postEsModel : postEsModelList) {
                PostListDto postListDTO = new PostListDto();
                BeanUtils.copyProperties(postEsModel, postListDTO);
                postListDTO.setAuthorInfo(memberFeignService.getMemberByUid(postEsModel.getMemberUid()));
                if (authService.getIsLogin()) {
                    postListDTO.setIsLike(postFeignService.isLike(authService.getLoginUid(), postEsModel.getUid()));
                } else {
                    postListDTO.setIsLike(false);
                }
                //获取Redis中文章总数缓存
                String key = RedisKeyConstant.POST_COUNT_KEY + postEsModel.getUid();
                if (redisUtil.hHasKey(key, RedisKeyConstant.POST_THUMB_COUNT_KEY)) {
                    postListDTO.setThumbCount((int) redisUtil.hget(key, RedisKeyConstant.POST_THUMB_COUNT_KEY));
                }
                if (redisUtil.hHasKey(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY)) {
                    postListDTO.setCommentCount((int) redisUtil.hget(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY));
                }
                postListDtos.add(postListDTO);
            }
            postSearchResponse.setPostList(postListDtos);

            //分页参数
            Assert.notNull(hits.getTotalHits(), "查询结果为空");
            long total = hits.getTotalHits().value;
            postSearchResponse.setTotal(total);
            postSearchResponse.setPageNum(postSearchParam.getCurPage());
            int totalPages = (int) (total % EsConstant.PAGE_SIZE) == 0 ? (int) (total / EsConstant.PAGE_SIZE) : (int) (total / EsConstant.PAGE_SIZE + 1);
            postSearchResponse.setTotalPages(totalPages);
            return Result.ok().put("data", postSearchResponse);
        } catch (IOException e) {
            throw new ServiceException(ExceptionUtil.getMessage(e));
        }
    }

    /**
     * 查询服务-文章
     *
     * @param hits            查询结果
     * @param searchHits      查询结果
     * @param postEsModelList 查询结果集合
     * @Description: 高亮
     */
    private void highlightHandle(SearchHits hits, SearchHit[] searchHits, List<PostEsModel> postEsModelList) {
        if (hits.getHits() != null && hits.getHits().length > 0) {
            PostEsModel postEsModel;
            for (SearchHit hit : searchHits) {
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                HighlightField title = highlightFields.get("title");
                HighlightField summary = highlightFields.get("summary");
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                if (title != null) {
                    Text[] fragments = title.fragments();
                    StringBuilder sb = new StringBuilder();
                    for (Text text : fragments) {
                        sb.append(text);
                    }
                    sourceAsMap.put("title", sb.toString());
                }
                if (summary != null) {
                    Text[] fragments = summary.fragments();
                    StringBuilder sb = new StringBuilder();
                    for (Text text : fragments) {
                        sb.append(text);
                    }
                    sourceAsMap.put("summary", sb.toString());
                }
                String jsonStr = JSONUtil.toJsonStr(sourceAsMap);
                postEsModel = JSONUtil.toBean(jsonStr, PostEsModel.class);
                postEsModelList.add(postEsModel);
            }
        }
    }

    /**
     * 逻辑删除es中的文章
     *
     * @param uid 文章uid
     */
    public void deleteSearchPost(String uid) throws IOException {
        UpdateRequest request = new UpdateRequest(POST_ES_INDEX, uid);
        PostEsModel postEsModel = new PostEsModel();
        postEsModel.setStatus(PostConstant.DELETE_STATUS);
        String postJson = JSONUtil.toJsonStr(postEsModel);
        request.doc(postJson, XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            log.info("文章删除成功");
        } else {
            log.info("文章删除失败!");
        }
    }

    /**
     * 持久化文章点赞评论数量等数据到es中
     *
     * @param post 文章实体类
     */
    public void transPost2ES(Post post) throws IOException {

        int id = post.getId();

        PostEsModel postEsModel = new PostEsModel();
        BeanUtils.copyProperties(post, postEsModel);

        String jsonStr = JSONUtil.toJsonStr(postEsModel);
        UpdateRequest request = new UpdateRequest(POST_ES_INDEX, String.valueOf(id));
        request.doc(jsonStr, XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        log.info("ElasticSearch文章信息更新完成，updateResponse=>{}", updateResponse);
    }
}
