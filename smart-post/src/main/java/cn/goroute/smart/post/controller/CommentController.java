package cn.goroute.smart.post.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.goroute.smart.post.entity.vo.CommentVo;
import cn.goroute.smart.common.utils.QueryParam;
import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.post.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author Alickx
 */
@RestController
@RequestMapping("smart/post/comment")
@Api(tags = "评论接口")
public class CommentController {

    @Autowired
    CommentService commentService;

    /**
     * 分页获取文章评论
     * @param queryParam 分页参数
     * @param postUid 文章uid
     * @return 评论结果
     */
    @PostMapping("/query/list")
    @ApiOperation(value = "分页获取文章评论", notes = "分页获取文章评论", httpMethod = "POST")
    @ApiParam(name = "queryParam", value = "分页参数", required = true)
    public Response getCommentByPost(@Validated @RequestBody QueryParam queryParam, @RequestParam Long postUid) throws IOException {
        return commentService.getCommentByPost(queryParam,postUid);
    }


    /**
     * 发布评论/回复
     * @param commentVo 评论vo
     * @return 评论结果
     */
    @SaCheckLogin
    @PostMapping("/save")
    @ApiOperation(value = "发布评论/回复", notes = "发布评论/回复", httpMethod = "POST")
    public Response save(@RequestBody CommentVo commentVo){

        return commentService.saveComment(commentVo);
    }

    /**
     * 删除评论
     * @param commentVo 评论vo
     * @return 删除结果
     */
    @SaCheckLogin
    @PostMapping("/delete")
    @ApiOperation(value = "删除评论", notes = "删除评论",httpMethod = "POST")
    public Response del(@RequestBody CommentVo commentVo){
        return commentService.del(commentVo);
    }
}
