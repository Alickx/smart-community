package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.post.constant.PostConstant;
import cn.goroute.smart.common.constant.RedisKeyConstant;
import cn.goroute.smart.common.entity.bo.EventRemindBo;
import cn.goroute.smart.common.exception.ServiceException;
import cn.goroute.smart.common.service.AuthService;
import cn.goroute.smart.common.utils.QueryParam;
import cn.goroute.smart.post.entity.pojo.Post;
import cn.goroute.smart.post.entity.pojo.Thumb;
import cn.goroute.smart.post.manage.IThumbManage;
import cn.goroute.smart.post.mapper.PostMapper;
import cn.goroute.smart.post.mapper.ThumbMapper;
import cn.goroute.smart.post.service.ThumbService;
import cn.goroute.smart.post.util.ConvertRemindUtil;
import cn.goroute.smart.post.util.RabbitmqUtil;
import cn.goroute.smart.redis.util.RedisUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Alickx
 * @description 针对表【t_thumb(点赞表)】的数据库操作Service实现
 * @createDate 2022-03-23 16:44:34
 */
@Slf4j
@Service
public class ThumbServiceImpl extends ServiceImpl<ThumbMapper, Thumb>
        implements ThumbService {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    PostMapper postMapper;

    @Autowired
    RabbitmqUtil rabbitmqUtil;

    @Resource
    ThumbMapper thumbMapper;

    @Autowired
    IThumbManage iThumbManage;

    @Autowired
    AuthService authService;

    /**
     * 点赞
     *
     * @param thumb 点赞类
     * @return 点赞结果
     */
    @Override
    public Response thumbSave(Thumb thumb) {

        //判断点赞文章是否存在
        Post post = postMapper.selectOne(new LambdaQueryWrapper<Post>()
                .eq(Post::getId, thumb.getPostId())
                .eq(Post::getStatus, PostConstant.NORMAL_STATUS));

        if (post == null) {
            return Response.error("文章不存在，点赞失败!");
        }

        // 判断是否已经点赞
        long loginUid = authService.getLoginUid();
        String redisKey = RedisKeyConstant.getThumbKey(loginUid, thumb.getToId());
        if (redisUtil.hHasKey(RedisKeyConstant.POST_THUMB_KEY, redisKey)) {
            Thumb thumbCache = (Thumb) redisUtil.hget(RedisKeyConstant.POST_THUMB_KEY, redisKey);
            if (Objects.equals(thumbCache.getStatus(), PostConstant.NORMAL_STATUS)) {
                return Response.error("已经点赞过了!");
            }
        }

        /*
          如果数据库中存在该记录，则是取消后再点赞
         */
        Thumb thumbEntity = thumbMapper.selectOne(new LambdaQueryWrapper<Thumb>()
                .eq(Thumb::getMemberId, loginUid)
                .eq(Thumb::getToId, thumb.getToId()));

        if (thumbEntity != null) {
            thumbEntity.setStatus(PostConstant.NORMAL_STATUS);
            thumbMapper.updateById(thumbEntity);
            incrPostThumbCount(thumb, post);
            return Response.success();
        }

        //设置点赞缓存
        thumb.setMemberId(loginUid);
        redisUtil.hset(RedisKeyConstant.POST_THUMB_KEY, redisKey, thumb, 60L * 60 * 24 * 2);

        //发送点赞事件提醒
        EventRemindBo eventRemindBo = ConvertRemindUtil.convertThumbNotification(thumb, post.getTitle());
        rabbitmqUtil.sendEventRemind(eventRemindBo);

        incrPostThumbCount(thumb, post);
        return Response.success();
    }


    private void incrPostThumbCount(Thumb thumb, Post post) {
        //设置帖子点赞数，帖子点赞数缓存
        String countKey = RedisKeyConstant.POST_COUNT_KEY + thumb.getPostId();
        if (redisUtil.hHasKey(countKey, RedisKeyConstant.POST_THUMB_COUNT_KEY)) {
            redisUtil.hincr(countKey, RedisKeyConstant.POST_THUMB_COUNT_KEY, 1);
        } else {
            redisUtil.hset(countKey, RedisKeyConstant.POST_THUMB_COUNT_KEY, post.getCommentCount());
            redisUtil.hincr(countKey, RedisKeyConstant.POST_THUMB_COUNT_KEY, 1);
        }
    }

    /**
     * 取消点赞
     *
     * @param thumb 点赞类
     * @return 取消结果
     */
    @Override
    public Response thumbCancel(Thumb thumb) {

        //判断点赞文章是否存在
        Post post = postMapper.selectOne(new LambdaQueryWrapper<Post>()
                .eq(Post::getId, thumb.getPostId())
                .eq(Post::getStatus, PostConstant.NORMAL_STATUS));

        if (post == null) {
            return Response.error("文章不存在，点赞失败!");
        }

        long loginUid = authService.getLoginUid();
        String redisKey = RedisKeyConstant.getThumbKey(loginUid, thumb.getToId());

        // 查询缓存中是否存在点赞记录
        if (redisUtil.hHasKey(RedisKeyConstant.POST_THUMB_KEY, redisKey)) {
            Thumb thumbCache = (Thumb) redisUtil.hget(RedisKeyConstant.POST_THUMB_KEY, redisKey);
            thumbCache.setStatus(PostConstant.THUMB_STATUS_CANCEL);
            redisUtil.hset(RedisKeyConstant.POST_THUMB_KEY, redisKey, thumbCache, 60L * 60 * 24 * 2);
        } else {
            // 如果缓存中没有点赞记录则查询数据库
            Thumb thumbEntity = thumbMapper.selectOne(new LambdaQueryWrapper<Thumb>()
                    .eq(Thumb::getMemberId, loginUid).eq(Thumb::getToId, thumb.getToId()));
            if (thumbEntity != null) {
                // 设置点赞状态为取消，并设置缓存（由定时任务来处理点赞数据的落地）
                thumbEntity.setStatus(PostConstant.THUMB_STATUS_CANCEL);
                redisUtil.hset(RedisKeyConstant.POST_THUMB_KEY, redisKey, thumbEntity, 60L * 60 * 24 * 2);
            }
        }

        String countKey = RedisKeyConstant.POST_COUNT_KEY + thumb.getPostId();
        if (!redisUtil.hHasKey(countKey, RedisKeyConstant.POST_THUMB_COUNT_KEY)) {
            long thumbCount = thumbMapper.selectCount(new LambdaQueryWrapper<Thumb>()
                    .eq(Thumb::getPostId, thumb.getPostId()));
            redisUtil.hset(countKey, RedisKeyConstant.POST_THUMB_COUNT_KEY, thumbCount);
        }
        redisUtil.hdecr(countKey, RedisKeyConstant.POST_THUMB_COUNT_KEY, 1);
        return Response.success();

    }

    /**
     * 持久化redis中用户的点赞数据到数据库
     *
     * @return 持久化成功条数
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int trans() {
        log.info("=>正在持久化redis点赞缓存");
        //模糊匹配500条key缓存
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyConstant.POST_THUMB_KEY, ScanOptions.scanOptions().count(500).build());
        //set集合去重
        Set<Thumb> insertList = new HashSet<>();
        Set<String> deleteList = new HashSet<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            Thumb thumb = (Thumb) entry.getValue();
            insertList.add(thumb);
            deleteList.add((String) entry.getKey());
        }
        try {
            cursor.close();
        } catch (IOException e) {
            throw new ServiceException(ExceptionUtil.getMessage(e));
        }
        //批量插入数据库
        for (Thumb thumb : insertList) {
            // 如果存在uid，则更新，否则插入
            if (thumb.getId() == null) {
                thumbMapper.insert(thumb);
            } else {
                thumbMapper.updateById(thumb);
            }
        }

        for (String key : deleteList) {
            redisUtil.hdel(RedisKeyConstant.POST_THUMB_KEY, key);
        }

        return insertList.size();
    }

    /**
     * 根据用户uid查询所有点赞记录
     *
     * @param queryParam 分页参数
     * @return 文章集合
     */
    @Override
    public Response listByMemberUid(QueryParam queryParam) {

        // TODO 重构

//        IPage<Thumb> thumbPage = thumbMapper.selectPage(new Query<Thumb>().getPage(queryParam),
//                new LambdaQueryWrapper<Thumb>()
//                        .eq(Thumb::getMemberId, queryParam.getUid())
//                        .orderByDesc(Thumb::getCreatedTime));
//
//        List<Long> postIdList = thumbPage.getRecords().stream().map(Thumb::getPostId).collect(Collectors.toList());
//
//        List<PostListDto> postDTOList = iThumbManage.getPostDTOListByPostIdList(postIdList);
//
//        PageUtils page = new PageUtils(thumbPage);
//
//        page.setList(postDTOList);
//
//        return Result.ok().put("data", page);
        return null;
    }

    /**
     * 持久化redis中文章的点赞数量到数据库
     *
     * @return 持久化成功条数
     */
    @Override
    public int transCount() {
        log.info("=>正在持久化文章信息缓存");
        String key = RedisKeyConstant.POST_COUNT_KEY + "*";
        Cursor<String> cursor = redisUtil.scan(key, 500);
        int count = 0;
        while (cursor.hasNext()) {
            count++;
            String hashKey = cursor.next();
            //分隔key获取文章id
            String[] split = hashKey.split(":");
            String postUid = split[split.length - 1];
            Post post = new Post();
            if (redisUtil.hHasKey(hashKey,RedisKeyConstant.POST_THUMB_COUNT_KEY)) {
                post.setThumbCount((long) redisUtil.hget(hashKey, RedisKeyConstant.POST_THUMB_COUNT_KEY));
            }
            if (redisUtil.hHasKey(hashKey,RedisKeyConstant.POST_COMMENT_COUNT_KEY)) {
                post.setCommentCount((long) redisUtil.hget(hashKey, RedisKeyConstant.POST_COMMENT_COUNT_KEY));
            }
            post.setId(Long.valueOf(postUid));
            postMapper.updateById(post);
            //消息队列异步更新es
            rabbitmqUtil.transPost2ESUtil(post);
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




