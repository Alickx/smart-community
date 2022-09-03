package cn.goroute.smart.search.controller;

import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.search.model.PostSearchParam;
import cn.goroute.smart.search.service.PostSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author Alickx
 */
@RestController
@RequestMapping("smart/search")
@Api(tags = "搜索接口")
public class SearchPostController {

    @Autowired
    PostSearchService postSearchService;


    @PostMapping("/post")
    @ApiOperation(value = "搜索帖子", notes = "搜索帖子", httpMethod = "POST")
    @ApiParam(name = "postSearchParam", value = "搜索参数", required = true)
    public Response searchPost(@RequestBody PostSearchParam postSearchParam) {
        return postSearchService.search(postSearchParam);
    }


    @GetMapping("/delete")
    @ApiOperation(value = "删除文章索引", notes = "删除文章索引", httpMethod = "GET")
    @ApiParam(name = "id", value = "文章id", required = true)
    public void deleteSearchPost(@RequestParam String id) throws IOException {
        postSearchService.deleteSearchPost(id);
    }

}
