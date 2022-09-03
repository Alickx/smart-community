package cn.goroute.smart.post.controller;

import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.common.utils.QueryParam;
import cn.goroute.smart.post.entity.vo.PostQueryVo;
import cn.goroute.smart.post.entity.vo.PostVo;
import cn.goroute.smart.post.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 文章表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:44:39
 */
@RestController
@Slf4j
@RequestMapping("smart/post/post")
@Api(tags = "文章接口")
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping("/query/list")
    @ApiOperation(value = "查询文章列表", notes = "查询文章列表", response = PostVo.class,httpMethod = "GET")
    @ApiParam(name = "postQueryVo", value = "文章查询对象", required = true)
    public Response queryBySection(PostQueryVo postQueryVO) {
        return postService.queryPage(postQueryVO);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation(value = "查询文章详情", notes = "查询文章详情", response = PostVo.class,httpMethod = "GET")
    @ApiParam(name = "id", value = "文章id", required = true)
    public Response info(@PathVariable("id") Long id) {
        return postService.getPostByUid(id);
    }

    /**
     * 发布/编辑文章
     * @param postVo 文章接受类
     * @return 发布/编辑结果
     */

    @PostMapping("/save")
    @ApiOperation(value = "发布/编辑文章", notes = "发布/编辑文章", response = PostVo.class,httpMethod = "POST")
    @ApiParam(name = "postVo", value = "文章vo", required = true)
    public Response save(@Validated @RequestBody PostVo postVo) {
        return postService.savePost(postVo);
    }
    /**
     * 删除文章
     */

    @PostMapping("/delete")
    @ApiOperation(value = "删除文章", notes = "删除文章", response = PostVo.class,httpMethod = "POST")
    @ApiParam(name = "postId", value = "文章id", required = true)
    public Response delete(@RequestParam Long postId) {
        return postService.deletePost(postId);
    }

    /**
     * 用户id查询文章列表
     * @param queryParam 用户id
     * @return 文章集合
     */
    @GetMapping("/query/list/member")
    @ApiOperation(value = "用户id查询文章列表", notes = "用户id查询文章列表", response = PostVo.class,httpMethod = "GET")
    @ApiParam(name = "queryParam", value = "查询参数", required = true)
    public Response listByMemberUid(QueryParam queryParam){
        return postService.listByMemberUid(queryParam);
    }

}
