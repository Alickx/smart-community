package cn.goroute.smart.post.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.goroute.smart.common.constant.RedisKeyConstant;
import cn.goroute.smart.common.utils.QueryParam;
import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.post.entity.pojo.Thumb;
import cn.goroute.smart.post.service.CommentService;
import cn.goroute.smart.post.service.ThumbService;
import cn.goroute.smart.redis.util.RedisUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Alickx
 */
@RestController
@RequestMapping("smart/post/thumb")
@Api(tags = "点赞接口")
public class ThumbController {

    @Autowired
    ThumbService thumbService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    CommentService commentService;

    @SaCheckLogin
    @PostMapping("/save")
    @ApiOperation(value = "点赞", notes = "点赞", httpMethod = "POST")
    @ApiParam(name = "thumb", value = "点赞实体", required = true)
    public Response save(@RequestBody Thumb thumb) {

        return thumbService.thumbSave(thumb);

    }

    @SaCheckLogin
    @PostMapping("/cancel")
    @ApiOperation(value = "取消点赞", notes = "取消点赞", httpMethod = "POST")
    @ApiParam(name = "thumb", value = "点赞实体", required = true)
    public Response cancel(@RequestBody Thumb thumb) {

        return thumbService.thumbCancel(thumb);
    }


    @PostMapping("/query/list")
    @ApiOperation(value = "查询点赞列表", notes = "查询点赞列表", httpMethod = "POST")
    @ApiParam(name = "queryParam", value = "查询参数", required = true)
    public Response listByMemberUid(@RequestBody QueryParam queryParam) {
        return thumbService.listByMemberUid(queryParam);
    }


    @RequestMapping("/is_like")
    @ApiOperation(value = "判断是否点赞", notes = "判断是否点赞", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginId", value = "用户id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "评论id", required = true, dataType = "String", paramType = "query")
    })
    public Boolean isLike(@RequestParam Long loginId, @RequestParam Long id) {
        String thumbRedisKey = RedisKeyConstant.getThumbKey(loginId, id);

        if (redisUtil.hHasKey(RedisKeyConstant.POST_THUMB_KEY, thumbRedisKey)) {
            return true;
        } else {
            //如果缓存不存在则去数据库中获取
            Thumb thumbResult = thumbService.getById(id);
            return thumbResult != null;
        }
    }

}
