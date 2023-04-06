package cn.goroute.smart.post.modules.article.controller;

import cn.goroute.smart.common.domain.PageParam;
import cn.goroute.smart.common.domain.PageResult;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.post.domain.entity.PostEntity;
import cn.goroute.smart.post.domain.form.PostQueryForm;
import cn.goroute.smart.post.modules.article.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2023/03/26/23:09
 * @Description:
 */
@RestController
@RequestMapping("/post/admin")
@RequiredArgsConstructor
public class PostAdminController {

    private final PostService postService;

    @GetMapping("/page")
    public R<PageResult<PostEntity>> page(PageParam pageParam, PostQueryForm form) {
        return R.ok(postService.pageQuery(pageParam, form));
    }

    @PostMapping("/update")
    public R<Boolean> update(PostEntity postEntity) {
        return R.ok(postService.updateById(postEntity));
    }

    @PostMapping("/delete")
    public R<Boolean> delete(Long postId) {
        return R.ok(postService.removeById(postId));
    }

    @PostMapping("/batchDelete")
    public R<Boolean> batchDelete(List<Long> ids) {
        return R.ok(postService.removeByIds(ids));
    }

}
