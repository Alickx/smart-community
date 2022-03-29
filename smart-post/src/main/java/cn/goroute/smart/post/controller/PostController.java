package cn.goroute.smart.post.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.goroute.smart.common.entity.vo.PostQueryListVO;
import cn.goroute.smart.common.entity.vo.PostVO;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.QueryParam;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.post.feign.MemberFeignService;
import cn.goroute.smart.post.service.CollectService;
import cn.goroute.smart.post.service.CommentService;
import cn.goroute.smart.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;


/**
 * 文章表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:44:39
 */
@RestController
@Slf4j
@RequestMapping("post/post")
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    CollectService collectService;

    @Autowired
    MemberFeignService memberFeignService;

    @PostMapping("/list")
    public Result withSectionList(@RequestBody QueryParam queryParam
            , @RequestParam(required = false) Integer sectionUid
            , @RequestParam(required = false) Integer tagUid) throws IOException {
        PageUtils page = postService.queryPage(queryParam, sectionUid, tagUid);
        return Result.ok().put("data", page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{uid}")
    public Result info(@PathVariable("uid") String uid) {
        return postService.getPostByUid(uid);
    }

    /**
     * 发布/编辑文章
     * @param postVo 文章接受类
     * @return 发布/编辑结果
     */
    @SaCheckLogin
    @PostMapping("/save")
    public Result save(@Valid @RequestBody PostVO postVo) {
        return postService.savePost(postVo);
    }
    /**
     * 删除文章
     */
    @SaCheckLogin
    @PostMapping("/delete")
    public Result delete(@RequestParam String postUid) {
        return postService.deletePost(postUid);
    }

    /**
     * 用户id查询文章列表
     * @param postQueryVo 文章查询vo
     * @return 文章集合
     */
    @PostMapping("/query_list")
    public Result listByMemberUid(@RequestBody PostQueryListVO postQueryVo){
        return postService.listByMemberUid(postQueryVo);
    }

}
