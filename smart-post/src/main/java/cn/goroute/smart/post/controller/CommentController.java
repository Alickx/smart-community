package cn.goroute.smart.post.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.dao.PostDao;
import cn.goroute.smart.common.entity.pojo.Comment;
import cn.goroute.smart.common.entity.pojo.PostEntity;
import cn.goroute.smart.common.entity.vo.CommentVO;
import cn.goroute.smart.common.utils.*;
import cn.goroute.smart.post.service.CommentService;
import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    IllegalTextCheckUtil illegalTextCheckUtil;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    PostDao postDao;

    /**
     * 分页获取文章评论
     * @param queryParam 分页参数
     * @param postUid 文章uid
     * @return 评论结果
     */
    @PostMapping("/list")
    public Result getCommentByPost(@RequestBody QueryParam queryParam, @RequestParam String postUid) throws IOException {
        return commentService.getCommentByPost(queryParam,postUid);
    }


    /**
     * 发布评论/回复
     * @param commentVo 评论vo
     * @return 评论结果
     */
    @PostMapping("/save")
    public Result save(@RequestBody CommentVO commentVo){

        //审核评论
        List<String> illegalList = illegalTextCheckUtil.checkText(commentVo.getContent());
        if (CollUtil.isNotEmpty(illegalList)) {
            return Result.error("请不要发送含有违禁词的评论");
        }
        Comment comment = new Comment();

        BeanUtils.copyProperties(commentVo,comment);

        comment.setMemberUid(StpUtil.getLoginIdAsString());
        commentService.save(comment);
        String key = RedisKeyConstant.POST_COUNT_KEY + commentVo.getPostUid();
        if (!redisUtil.hHasKey(key,RedisKeyConstant.POST_COMMENT_COUNT_KEY)) {
            synchronized (this) {
                PostEntity postEntity = postDao.selectById(commentVo.getPostUid());
                redisUtil.hset(key,RedisKeyConstant.POST_COMMENT_COUNT_KEY,postEntity.getCommentCount());
            }
        }
        redisUtil.hincr(key,RedisKeyConstant.POST_COMMENT_COUNT_KEY,1);

        return Result.ok().put("data",comment.getUid());
    }

    @PostMapping("/del")
    public Result del(@RequestBody CommentVO commentVo){
        return commentService.del(commentVo);
    }
}
