package cn.goroute.smart.post.controller;

import cn.goroute.smart.common.entity.PostEntity;
import cn.goroute.smart.common.entity.PostVo;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.R;
import cn.goroute.smart.common.utils.RedisUtil;
import cn.goroute.smart.post.feign.MemberFeignService;
import cn.goroute.smart.post.service.CollectService;
import cn.goroute.smart.post.service.CommentService;
import cn.goroute.smart.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;


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

    @Autowired
    RedisUtil redisUtil;


    @GetMapping("/list")
    public R withSectionList(@RequestParam Integer curPage
            , @RequestParam(required = false) Integer sectionUid
            , @RequestParam(required = false) Integer tagUid) throws IOException {
        PageUtils page = postService.queryPage(curPage, sectionUid, tagUid);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{uid}")
    public R info(@PathVariable("uid") String uid) {
        return postService.getPostByUid(uid);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@Valid @RequestBody PostVo postVo) {
        return postService.savePost(postVo);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody PostEntity post) {
        postService.updateById(post);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] uids) {
        postService.removeByIds(Arrays.asList(uids));

        return R.ok();
    }

}
