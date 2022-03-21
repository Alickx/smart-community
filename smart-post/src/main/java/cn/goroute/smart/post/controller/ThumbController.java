package cn.goroute.smart.post.controller;

import cn.goroute.smart.common.entity.pojo.Comment;
import cn.goroute.smart.common.entity.vo.CommentVO;
import cn.goroute.smart.common.entity.vo.PostQueryListVO;
import cn.goroute.smart.common.utils.RedisKeyConstant;
import cn.goroute.smart.common.utils.RedisUtil;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.post.service.CommentService;
import cn.goroute.smart.post.service.ThumbService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Alickx
 */
@RestController
@RequestMapping("post/thumb")
public class ThumbController {

    @Autowired
    ThumbService thumbService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    CommentService commentService;

    @PostMapping("/save")
    public Result save(@RequestBody CommentVO thumbVo) {
        //存入缓存
        return thumbService.thumbSave(thumbVo);

    }

    @PostMapping("/cancel")
    public Result cancel(@RequestBody CommentVO thumbVo) {

        return thumbService.thumbCancel(thumbVo);
    }

    @PostMapping("/query_list")
    public Result listByMemberUid(@RequestBody PostQueryListVO postQueryListVO) {
        return thumbService.listByMemberUid(postQueryListVO);
    }

    @RequestMapping("/is_like")
    public Boolean isLike(@RequestParam String loginIdAsString,@RequestParam String uid) {
        String thumbRedisKey = RedisKeyConstant.getThumbOrCollectKey(loginIdAsString, uid);
        if (redisUtil.hHasKey(RedisKeyConstant.POST_THUMB_KEY, thumbRedisKey)) {
            return true;
        } else {
            //如果缓存不存在则去数据库中获取
            Comment thumbResult = commentService.getOne(new QueryWrapper<Comment>()
                    .eq("member_uid", loginIdAsString)
                    .eq("to_uid", uid));
            return thumbResult != null;
        }
    }

}
