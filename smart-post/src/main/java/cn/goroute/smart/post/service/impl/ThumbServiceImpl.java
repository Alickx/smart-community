package cn.goroute.smart.post.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.entity.Comment;
import cn.goroute.smart.common.entity.CommentVo;
import cn.goroute.smart.common.utils.R;
import cn.goroute.smart.common.utils.RedisUtil;
import cn.goroute.smart.post.service.CommentService;
import cn.goroute.smart.post.service.ThumbService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.goroute.smart.common.utils.Constant.CloudService.getThumbOrCollectKey;
import static cn.goroute.smart.common.utils.Constant.POST_THUMB_KEY;

@Service
@Slf4j
public class ThumbServiceImpl implements ThumbService {


    @Autowired
    CommentService commentService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedisTemplate redisTemplate;

    @Override

    public R thumbSave(CommentVo thumbVo) {

        if (thumbVo.getToUid() == null) {
            return R.error();
        }

        String loginIdAsString = StpUtil.getLoginIdAsString();

        String redisKey = getThumbOrCollectKey(loginIdAsString, thumbVo.getToUid());

        Object thumbRedis = redisUtil.hget(POST_THUMB_KEY, redisKey);

        if (thumbRedis != null && (int) thumbRedis == 1) {
            return R.ok();
        }

        String toUid = thumbVo.getToUid();

        Comment commentEntity = commentService.getOne(new QueryWrapper<Comment>()
                .eq("member_uid", loginIdAsString)
                .eq("to_uid", toUid));
        if (commentEntity != null) {
            return R.ok();
        }

        redisUtil.hset(POST_THUMB_KEY, redisKey, 1, 60 * 60 * 24 * 2);

        return R.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R thumbCancel(CommentVo thumbVo) {

        if (thumbVo.getToUid() == null) {
            return R.error();
        }

        String loginIdAsString = StpUtil.getLoginIdAsString();

        String redisKey = getThumbOrCollectKey(loginIdAsString, thumbVo.getToUid());

        String toUid = thumbVo.getToUid();

        Comment commentEntity = commentService.getOne(new QueryWrapper<Comment>()
                .eq("member_uid", loginIdAsString)
                .eq("to_uid", toUid));
        if (commentEntity != null) {
            commentService.removeById(commentEntity.getUid());
        }

        if (redisUtil.hHasKey(POST_THUMB_KEY, redisKey)) {
            redisUtil.hdel(POST_THUMB_KEY, redisKey);
        }

        return R.ok();

    }

    @Override
    public int trans() {
        log.info("=>正在持久化redis点赞缓存");
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(POST_THUMB_KEY, ScanOptions.NONE);
        List<Comment> insertList = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            String[] split = key.split("::");
            String memberUid = split[0];
            String targetUid = split[1];
            Comment comment = new Comment();
            comment.setMemberUid(memberUid);
            comment.setToUid(targetUid);
            comment.setType(1);
            insertList.add(comment);
            redisTemplate.opsForHash().delete(POST_THUMB_KEY, key);
        }
        commentService.saveBatch(insertList);
        log.info("=>持久化成功！一共持久化了{}条缓存",insertList.size());
        return insertList.size();
    }
}
