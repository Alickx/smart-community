package cn.goroute.smart.post.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.dao.CommentDao;
import cn.goroute.smart.common.dao.PostDao;
import cn.goroute.smart.common.entity.dto.PostListDTO;
import cn.goroute.smart.common.entity.pojo.Comment;
import cn.goroute.smart.common.entity.pojo.PostEntity;
import cn.goroute.smart.common.entity.vo.CommentVO;
import cn.goroute.smart.common.entity.vo.PostQueryListVO;
import cn.goroute.smart.common.exception.ServiceException;
import cn.goroute.smart.common.utils.*;
import cn.goroute.smart.post.service.CommentService;
import cn.goroute.smart.post.service.ThumbService;
import cn.goroute.smart.post.util.PostRabbitmqUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class ThumbServiceImpl extends ServiceImpl<CommentDao, Comment>
        implements ThumbService {


    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    PostServiceImpl postService;

    @Autowired
    CommentDao commentDao;

    @Autowired
    CommentService commentService;

    @Autowired
    PostDao postDao;

    @Autowired
    PostRabbitmqUtil postRabbitmqUtil;

    /**
     * 点赞
     *
     * @param thumbVo 点赞vo类
     * @return 点赞结果
     */
    @Override
    public Result thumbSave(CommentVO thumbVo) {

        if (thumbVo.getToUid() == null) {
            return Result.error();
        }

        String loginIdAsString = StpUtil.getLoginIdAsString();

        String redisKey = RedisKeyConstant.getThumbOrCollectKey(loginIdAsString, thumbVo.getToUid());

        Object thumbRedis = redisUtil.hget(RedisKeyConstant.POST_THUMB_KEY, redisKey);

        if (thumbRedis != null && (int) thumbRedis == 1) {
            return Result.ok();
        }

        String toUid = thumbVo.getToUid();

        Comment commentEntity = commentDao.selectOne(new QueryWrapper<Comment>()
                .eq("member_uid", loginIdAsString)
                .eq("to_uid", toUid));
        if (commentEntity != null) {
            return Result.ok();
        }

        redisUtil.hset(RedisKeyConstant.POST_THUMB_KEY, redisKey, 1, 60 * 60 * 24 * 2);

        String countKey = RedisKeyConstant.POST_COUNT_KEY + thumbVo.getToUid();
        if (!redisUtil.hHasKey(countKey, RedisKeyConstant.POST_THUMB_COUNT_KEY)) {
            int thumbCount = commentDao.selectCount(new QueryWrapper<Comment>()
                    .eq("type", 1)
                    .eq("to_uid", thumbVo.getToUid()));
            redisUtil.hset(countKey, RedisKeyConstant.POST_THUMB_COUNT_KEY, thumbCount);
        }
        redisUtil.hincr(countKey, RedisKeyConstant.POST_THUMB_COUNT_KEY, 1);
        return Result.ok();
    }

    /**
     * 取消点赞
     *
     * @param thumbVo 点赞vo类
     * @return 取消结果
     */
    @Override
    public Result thumbCancel(CommentVO thumbVo) {

        if (thumbVo.getToUid() == null) {
            return Result.error();
        }

        String loginIdAsString = StpUtil.getLoginIdAsString();

        String redisKey = RedisKeyConstant.getThumbOrCollectKey(loginIdAsString, thumbVo.getToUid());

        String toUid = thumbVo.getToUid();

        Comment commentEntity = commentDao.selectOne(new QueryWrapper<Comment>()
                .eq("member_uid", loginIdAsString)
                .eq("to_uid", toUid));
        //如果点赞记录不为空，则删除点赞
        if (commentEntity != null) {
            commentDao.deleteById(commentEntity.getUid());
        }
        String countKey = RedisKeyConstant.POST_COUNT_KEY + thumbVo.getToUid();
        if (redisUtil.hHasKey(RedisKeyConstant.POST_THUMB_KEY, redisKey)) {
            redisUtil.hdel(RedisKeyConstant.POST_THUMB_KEY, redisKey);
        }
        if (!redisUtil.hHasKey(countKey, RedisKeyConstant.POST_THUMB_COUNT_KEY)) {
            int thumbCount = commentDao.selectCount(new QueryWrapper<Comment>()
                    .eq("type", 1)
                    .eq("to_uid", thumbVo.getToUid()));
            redisUtil.hset(countKey, RedisKeyConstant.POST_THUMB_COUNT_KEY, thumbCount);
        }
        redisUtil.hdecr(countKey, RedisKeyConstant.POST_THUMB_COUNT_KEY, 1);
        return Result.ok();

    }

    /**
     * 持久化redis中用户的点赞数据到数据库
     *
     * @return 持久化成功条数
     */
    @Override
    public int trans() {
        log.info("=>正在持久化redis点赞缓存");
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyConstant.POST_THUMB_KEY, ScanOptions.NONE);
        //set集合去重
        Set<Comment> insertList = new HashSet<>();
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
            redisTemplate.opsForHash().delete(RedisKeyConstant.POST_THUMB_KEY, key);
        }
        try {
            cursor.close();
        } catch (IOException e) {
            throw new ServiceException(ExceptionUtil.getMessage(e));
        }
        commentService.saveBatch(insertList);
        log.info("=>持久化成功！一共持久化了{}条缓存", insertList.size());
        return insertList.size();
    }

    /**
     * 根据用户uid查询所有点赞记录
     *
     * @param postQueryListVO 分页参数
     * @return 文章集合
     */
    @Override
    public Result listByMemberUid(PostQueryListVO postQueryListVO) {
        //TODO 加上redis刚缓存完的点赞数
        IPage<Comment> page = this.page(
                new Query<Comment>().getPage(postQueryListVO),
                new QueryWrapper<Comment>().eq("type", Constant.POST_THUMB_TYPE)
                        .eq("member_uid", postQueryListVO.getMemberUid())
        );

        List<Comment> commentRecords = page.getRecords();
        if (CollectionUtil.isEmpty(commentRecords)) {
            return Result.ok().put("data", new ArrayList<>(1));
        }
        List<String> postIds = new ArrayList<>();
        commentRecords.forEach(c -> {
            postIds.add(c.getToUid());
        });

        try {
            List<PostListDTO> postDTOList = postService.getPostDTOList(postIds);
            return Result.ok().put("data", postDTOList);

        } catch (IOException e) {
            log.error("查询用户点赞记录错误,e={}", e.getMessage());
        }
        return Result.error();

    }

    /**
     * 持久化redis中文章的点赞评论数量到数据库
     *
     * @return 持久化成功条数
     */
    @Override
    public int transCount() {
        log.info("=>正在持久化文章信息缓存");
        String key = RedisKeyConstant.POST_COUNT_KEY + "*";
        Cursor<String> cursor = redisUtil.scan(key, 1000);
        int count = 0;
        while (cursor.hasNext()) {
            count++;
            String hashKey = cursor.next();
            //分隔key获取文章id
            String[] split = hashKey.split(":");
            String postUid = split[split.length - 1];
            int thumbCount = (int) redisUtil.hget(hashKey, RedisKeyConstant.POST_THUMB_COUNT_KEY);
            int commentCount = (int) redisUtil.hget(hashKey, RedisKeyConstant.POST_COMMENT_COUNT_KEY);
            PostEntity postEntity = new PostEntity();
            postEntity.setUid(postUid);
            postEntity.setCommentCount(commentCount);
            postEntity.setThumbCount(thumbCount);
            postDao.updateById(postEntity);
            //消息队列异步更新es
            postRabbitmqUtil.transPostCount2ES(postEntity);
            redisUtil.del(hashKey);
        }
        try {
            cursor.close();
        } catch (IOException e) {
            throw new ServiceException(ExceptionUtil.getMessage(e));
        }
        log.info("=>持久化成功！一共持久化了{}条缓存", count);
        return 0;
    }
}
