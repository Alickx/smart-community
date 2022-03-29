package cn.goroute.smart.post.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.dao.PostDao;
import cn.goroute.smart.common.dao.ThumbDao;

import cn.goroute.smart.common.entity.pojo.EventRemind;
import cn.goroute.smart.common.entity.pojo.Post;
import cn.goroute.smart.common.entity.pojo.Thumb;
import cn.goroute.smart.common.entity.vo.PostQueryListVO;
import cn.goroute.smart.common.exception.BizCodeEnum;
import cn.goroute.smart.common.exception.ServiceException;
import cn.goroute.smart.common.utils.*;
import cn.goroute.smart.post.service.ThumbService;
import cn.goroute.smart.post.util.ConvertRemindUtil;
import cn.goroute.smart.post.util.RabbitmqUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Alickx
 * @description 针对表【t_thumb(点赞表)】的数据库操作Service实现
 * @createDate 2022-03-23 16:44:34
 */
@Slf4j
@Service
public class ThumbServiceImpl extends ServiceImpl<ThumbDao, Thumb>
        implements ThumbService {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    PostDao postDao;

    @Autowired
    RabbitmqUtil rabbitmqUtil;

    @Resource
    ThumbDao thumbDao;

    /**
     * 点赞
     *
     * @param thumb 点赞类
     * @return 点赞结果
     */
    @Override
    public Result thumbSave(Thumb thumb) {

        //判断点赞文章是否存在
        Post post = postDao.selectOne(new LambdaQueryWrapper<Post>()
                .eq(Post::getUid, thumb.getPostUid())
                .eq(Post::getStatus, PostConstant.NORMAL_STATUS)
                .eq(Post::getIsPublish, PostConstant.PUBLISH));

        if (post == null) {
            return Result.error(BizCodeEnum.POST_NOT_EXIST.getCode(), BizCodeEnum.POST_NOT_EXIST.getMessage());
        }

        // 判断是否已经点赞
        String loginIdAsString = StpUtil.getLoginIdAsString();
        String redisKey = RedisKeyConstant.getThumbKey(loginIdAsString, thumb.getToUid());
        if (redisUtil.hHasKey(RedisKeyConstant.POST_THUMB_KEY, redisKey)) {
            return Result.error("已经点赞过了");
        }

        /*
          如果数据库中存在该记录，则证明是取消后再点赞
         */
        Thumb thumbEntity = thumbDao.selectOne(new LambdaQueryWrapper<Thumb>()
                .eq(Thumb::getMemberUid, loginIdAsString)
                .eq(Thumb::getToUid, thumb.getToUid()));

        if (thumbEntity != null) {
            thumbEntity.setStatus(PostConstant.NORMAL_STATUS);
            thumbDao.updateById(thumbEntity);
            return Result.ok();
        }

        //设置点赞缓存
        thumb.setMemberUid(loginIdAsString);
        redisUtil.hset(RedisKeyConstant.POST_THUMB_KEY, redisKey, thumb, 60L * 60 * 24 * 2);

        //发送点赞事件提醒
        EventRemind eventRemind = ConvertRemindUtil.convertThumbNotification(thumb, post.getTitle());
        rabbitmqUtil.sendEventRemind(eventRemind);

        //设置帖子点赞数，帖子点赞数缓存
        String countKey = RedisKeyConstant.POST_COUNT_KEY + thumb.getPostUid();
        if (redisUtil.hHasKey(countKey, RedisKeyConstant.POST_THUMB_COUNT_KEY)) {
            redisUtil.hincr(countKey, RedisKeyConstant.POST_THUMB_COUNT_KEY, 1);
        } else {
            synchronized (this) {
                if (redisUtil.hHasKey(countKey, RedisKeyConstant.POST_THUMB_COUNT_KEY)) {
                    redisUtil.hincr(countKey, RedisKeyConstant.POST_THUMB_COUNT_KEY, 1);
                } else {
                    redisUtil.hset(countKey, RedisKeyConstant.POST_THUMB_COUNT_KEY, post.getCommentCount());
                    redisUtil.hincr(countKey, RedisKeyConstant.POST_THUMB_COUNT_KEY, 1);
                }
            }
        }
        return Result.ok();
    }

    /**
     * 取消点赞
     *
     * @param thumb 点赞类
     * @return 取消结果
     */
    @Override
    public Result thumbCancel(Thumb thumb) {

        String loginIdAsString = StpUtil.getLoginIdAsString();
        String redisKey = RedisKeyConstant.getThumbKey(loginIdAsString, thumb.getToUid());

        Thumb thumbEntity = thumbDao.selectOne(new LambdaQueryWrapper<Thumb>()
                .eq(Thumb::getMemberUid, loginIdAsString).eq(Thumb::getToUid, thumb.getToUid()));

        //如果点赞记录不为空，则删除点赞
        if (thumbEntity != null) {
            thumbEntity.setStatus(PostConstant.DELETE_STATUS);
            thumbDao.updateById(thumbEntity);
        } else {
            if (redisUtil.hHasKey(RedisKeyConstant.POST_THUMB_KEY, redisKey)) {
                redisUtil.hdel(RedisKeyConstant.POST_THUMB_KEY, redisKey);
            }
        }

        String countKey = RedisKeyConstant.POST_COUNT_KEY + thumb.getPostUid();
        if (!redisUtil.hHasKey(countKey, RedisKeyConstant.POST_THUMB_COUNT_KEY)) {
            Integer thumbCount = thumbDao.selectCount(new LambdaQueryWrapper<Thumb>()
                    .eq(Thumb::getPostUid, thumb.getPostUid()));
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
        boolean saveResult = this.saveBatch(insertList);
        if (saveResult) {
            log.info("=>持久化成功！一共持久化了{}条缓存", insertList.size());
            //循环删除redis缓存
            for (String key : deleteList) {
                redisUtil.hdel(RedisKeyConstant.POST_THUMB_KEY, key);
            }
            return insertList.size();
        } else {
            throw new ServiceException("点赞信息持久化失败！");
        }
    }

    /**
     * 根据用户uid查询所有点赞记录
     *
     * @param postQueryListVO 分页参数
     * @return 文章集合
     */
    @Override
    public Result listByMemberUid(PostQueryListVO postQueryListVO) {

        IPage<Thumb> thumbPage = thumbDao.selectPage(new Query<Thumb>().getPage(postQueryListVO),
                new LambdaQueryWrapper<Thumb>()
                        .eq(Thumb::getMemberUid, postQueryListVO.getMemberUid()));
        PageUtils page = new PageUtils(thumbPage);
        return Result.ok().put("data", page);
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
            int thumbCount = (int) redisUtil.hget(hashKey, RedisKeyConstant.POST_THUMB_COUNT_KEY);
            int commentCount = (int) redisUtil.hget(hashKey, RedisKeyConstant.POST_COMMENT_COUNT_KEY);
            Post post = new Post();
            post.setUid(postUid);
            post.setCommentCount(commentCount);
            post.setThumbCount(thumbCount);
            postDao.updateById(post);
            //消息队列异步更新es
            rabbitmqUtil.transPostCount2ES(post);
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




