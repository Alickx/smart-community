package cn.goroute.smart.post.controller;

import cn.goroute.smart.post.entity.vo.PostQueryVo;
import cn.goroute.smart.post.entity.vo.PostVo;
import cn.goroute.smart.common.utils.QueryParam;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.post.service.PostService;
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
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping("/query/list")
    public Result queryBySection(PostQueryVo postQueryVO) {
        return postService.queryPage(postQueryVO);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{uid}")
    public Result info(@PathVariable("uid") Long uid) {
        return postService.getPostByUid(uid);
    }

    /**
     * 发布/编辑文章
     * @param postVo 文章接受类
     * @return 发布/编辑结果
     */

    @PostMapping("/save")
    public Result save(@Validated @RequestBody PostVo postVo) {
        return postService.savePost(postVo);
    }
    /**
     * 删除文章
     */

    @PostMapping("/delete")
    public Result delete(@RequestParam Long postUid) {
        return postService.deletePost(postUid);
    }

    /**
     * 用户id查询文章列表
     * @param queryParam 用户id
     * @return 文章集合
     */
    @GetMapping("/query/list/member")
    public Result listByMemberUid(QueryParam queryParam){
        return postService.listByMemberUid(queryParam);
    }

}
