package cn.goroute.smart.post.manage.impl;

import cn.goroute.smart.post.constant.PostConstant;
import cn.goroute.smart.common.constant.RedisKeyConstant;
import cn.goroute.smart.common.feign.MemberFeignService;
import cn.goroute.smart.post.entity.pojo.Thumb;
import cn.goroute.smart.post.manage.IPostManage;
import cn.goroute.smart.post.mapper.PostMapper;
import cn.goroute.smart.post.mapper.ThumbMapper;
import cn.goroute.smart.redis.util.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Alickx
 * @Date: 2022/06/08/8:06
 * @Description:
 */
@Service
public class PostManageImpl implements IPostManage {

    @Autowired
    PostMapper postMapper;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    ThumbMapper thumbMapper;

    @Autowired
    MemberFeignService memberFeignService;


    /**
     * 获取是否点赞或收藏
     *
     * @param id      目标id
     * @param loginId 用户id
     * @param type     类型 0 = 点赞  1 = 收藏
     * @return 是否点赞或是否收藏
     */
    @Override
    public boolean checkIsThumbOrCollect(Long id, Long loginId, int type) {
        boolean result = false;
        /*
          判断是否点赞或是否收藏
         */
        if (type == 0) {
            String thumbRedisKey = RedisKeyConstant.getThumbKey(loginId, id);
            if (redisUtil.hHasKey(RedisKeyConstant.POST_THUMB_KEY, thumbRedisKey)) {
                result = true;
            } else {
                //如果缓存不存在则去数据库中获取
                Thumb thumbResult = thumbMapper.selectOne(new LambdaQueryWrapper<Thumb>()
                        .eq(Thumb::getMemberId, loginId)
                        .eq(Thumb::getType, PostConstant.THUMB_POST_TYPE)
                        .eq(Thumb::getPostId, id));
                if (thumbResult != null) {
                    result = true;
                }
            }
        } else if (type == 1) {
            String collectRedisKey = RedisKeyConstant.getThumbKey(loginId, id);
            if (redisUtil.hHasKey(RedisKeyConstant.POST_COLLECT_KEY, collectRedisKey)) {
                result = true;
            } else {
                // TODO 调用用户feign接口
//                Collect collectResult = collectMapper.selectOne(new LambdaQueryWrapper<Collect>()
//                        .eq(Collect::getMemberId, loginId)
//                        .eq(Collect::getPostId, id));
//                if (collectResult != null) {
//                    result = true;
//                }
            }
        } else {
            return false;
        }
        return result;
    }

    /**
     * 获取文章的总点赞
     *
     * @param postUid 文章主键
     * @return 点赞数
     */
    @Override
    public int getThumbCount(Long postUid) {

        //先从缓存中获取再去数据库中获取
        String key = RedisKeyConstant.POST_COUNT_KEY + postUid;
        if (redisUtil.hHasKey(key, RedisKeyConstant.POST_THUMB_COUNT_KEY)) {
            return (int) redisUtil.hget(key, RedisKeyConstant.POST_THUMB_COUNT_KEY);
        }

        //缓存获取不到就数据库获取
        int thumbCount;
        synchronized (this) {
            if (redisUtil.hHasKey(key, RedisKeyConstant.POST_THUMB_COUNT_KEY)) {
                return (int) redisUtil.hget(key, RedisKeyConstant.POST_THUMB_COUNT_KEY);
            }

            thumbCount = postMapper.selectThumbCount(postUid);
            redisUtil.hset(key, RedisKeyConstant.POST_THUMB_COUNT_KEY, thumbCount);
        }
        return thumbCount;
    }

    /**
     * 获取文章的评论总数
     *
     * @param postId 文章实体类
     * @return 文章评论总数
     */
    @Override
    public int getCommentCount(Long postId) {


        String key = RedisKeyConstant.POST_COUNT_KEY + postId;
        if (redisUtil.hHasKey(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY)) {
            return (int) redisUtil.hget(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY);
        }
        int commentCountResult;
        synchronized (this) {
            if (redisUtil.hHasKey(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY)) {
                return (int) redisUtil.hget(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY);
            }
            commentCountResult = postMapper.getCommentCount(postId);
            redisUtil.hset(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY, commentCountResult);
        }
        return commentCountResult;
    }
}
