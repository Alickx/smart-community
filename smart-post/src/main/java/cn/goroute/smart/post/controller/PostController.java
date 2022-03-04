package cn.goroute.smart.post.controller;

import cn.goroute.smart.common.entity.PostDTO;
import cn.goroute.smart.common.entity.PostEntity;
import cn.goroute.smart.common.entity.PostVo;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.R;
import cn.goroute.smart.post.feign.MemberFeignService;
import cn.goroute.smart.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;


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
    private PostService postService;

    @Autowired
    MemberFeignService memberFeignService;

    /**
     * 指定板块标签列表
     */
    @GetMapping("/list/{sectionUid}/{tagUid}")
    public R withSectionAndTagList(@RequestParam Map<String, Object> params
            , @PathVariable String sectionUid, @PathVariable String tagUid) {
        PageUtils page = postService.queryPage(params, sectionUid, tagUid);
        return R.ok().put("page", page);
    }

    @GetMapping("/list/{sectionUid}")
    public R withSectionList(@RequestParam Map<String, Object> params, @PathVariable String sectionUid) {
        PageUtils page = postService.queryPage(params, sectionUid);
        return R.ok().put("page", page);
    }


    @GetMapping("/list")
    public R withSectionList(@RequestParam Map<String, Object> params) {
        PageUtils page = postService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{uid}")
    public R info(@PathVariable("uid") String uid) {
        PostEntity post = postService.getById(uid);
        R memberInfo = memberFeignService.info(post.getMemberUid());
        PostDTO postDTO = new PostDTO();
        BeanUtils.copyProperties(post, postDTO);
        return Objects.requireNonNull(R.ok().put("post", postDTO)).put("memberInfo", memberInfo.get("memberInfo"));
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
