package cn.goroute.smart.post.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.entity.Comment;
import cn.goroute.smart.common.entity.CommentVo;
import cn.goroute.smart.post.service.CommentService;
import cn.goroute.smart.common.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/post/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/list")
    public R getCommentByPost(@RequestParam String postUid) throws IOException {
        return commentService.getCommentByPost(postUid);
    }


    @PostMapping("/save")
    public R save(@RequestBody CommentVo commentVo){

        Comment comment = new Comment();

        BeanUtils.copyProperties(commentVo,comment);

        comment.setMemberUid(StpUtil.getLoginIdAsString());
        commentService.save(comment);

        return R.ok();
    }

}
