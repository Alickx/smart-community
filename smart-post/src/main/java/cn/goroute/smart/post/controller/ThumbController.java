package cn.goroute.smart.post.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.goroute.smart.common.constant.RedisKeyConstant;
import cn.goroute.smart.post.entity.pojo.Thumb;
import cn.goroute.smart.common.utils.QueryParam;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.post.service.CommentService;
import cn.goroute.smart.post.service.ThumbService;
import cn.goroute.smart.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Alickx
 */
@RestController
@RequestMapping("smart/post/thumb")
public class ThumbController {

    @Autowired
    ThumbService thumbService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    CommentService commentService;

    @SaCheckLogin
    @PostMapping("/save")
    public Result save(@RequestBody Thumb thumb) {
        //存入缓存
        return thumbService.thumbSave(thumb);

    }

    @SaCheckLogin
    @PostMapping("/cancel")
    public Result cancel(@RequestBody Thumb thumb) {

        return thumbService.thumbCancel(thumb);
    }


    @PostMapping("/query/list")
    public Result listByMemberUid(@RequestBody QueryParam queryParam) {
        return thumbService.listByMemberUid(queryParam);
    }


    @RequestMapping("/is_like")
    public Boolean isLike(@RequestParam Long loginUid, @RequestParam Long uid) {
        String thumbRedisKey = RedisKeyConstant.getThumbKey(loginUid, uid);

        if (redisUtil.hHasKey(RedisKeyConstant.POST_THUMB_KEY, thumbRedisKey)) {
            return true;
        } else {
            //如果缓存不存在则去数据库中获取
            Thumb thumbResult = thumbService.getById(uid);
            return thumbResult != null;
        }
    }

}
