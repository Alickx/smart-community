package cn.goroute.smart.post.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.goroute.smart.common.entity.vo.CommentVO;
import cn.goroute.smart.common.utils.QueryParam;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.post.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author Alickx
 */
@RestController
@RequestMapping("/post/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    /**
     * 分页获取文章评论
     * @param queryParam 分页参数
     * @param postUid 文章uid
     * @return 评论结果
     */
    @PostMapping("/list")
    // TODO Valid不生效
    public Result getCommentByPost(@RequestBody QueryParam queryParam, @RequestParam String postUid) throws IOException {
        return commentService.getCommentByPost(queryParam,postUid);
    }


    /**
     * 发布评论/回复
     * @param commentVo 评论vo
     * @return 评论结果
     */
    @SaCheckLogin
    @PostMapping("/save")
    public Result save(@RequestBody CommentVO commentVo){

        return commentService.saveComment(commentVo);
    }

    /**
     * 删除评论
     * @param commentVo 评论vo
     * @return 删除结果
     */
    @SaCheckLogin
    @PostMapping("/del")
    public Result del(@RequestBody CommentVO commentVo){
        return commentService.del(commentVo);
    }
}
