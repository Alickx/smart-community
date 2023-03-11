package cn.goroute.smart.user.modules.follow.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.user.constant.UserRedisConstant;
import cn.goroute.smart.user.domain.entity.UserFollowEntity;
import cn.goroute.smart.user.domain.form.UserSaveFollowForm;
import cn.goroute.smart.user.domain.vo.UserFollowVO;
import cn.goroute.smart.user.domain.vo.UserProfileVO;
import cn.goroute.smart.user.modules.follow.manager.UserFollowManagerService;
import cn.goroute.smart.user.modules.follow.mapper.UserFollowMapper;
import cn.goroute.smart.user.modules.follow.mq.UserFollowEventTemplate;
import cn.goroute.smart.user.modules.follow.service.UserFollowService;
import cn.goroute.smart.user.modules.profile.service.UserProfileService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author caiguopeng
 * @description 针对表【user_follow(用户关注表)】的数据库操作Service实现
 * @createDate 2022-09-17 19:30:44
 */
@RequiredArgsConstructor
@Service
public class UserFollowServiceImpl extends ServiceImpl<UserFollowMapper, UserFollowEntity>
        implements UserFollowService {

    private final RedisUtil redisUtil;
    private final UserFollowManagerService userFollowManagerService;
    private final UserProfileService userProfileService;
    private final UserFollowMapper userFollowMapper;
	private final UserFollowEventTemplate userFollowEventTemplate;

	@Override
    public Boolean saveFollow(UserSaveFollowForm userSaveFollowForm) {

        long userId = StpUtil.getLoginIdAsLong();

        // 判断是否是操作自己
        if (Objects.equals(userId, userSaveFollowForm.getToUserId())) {
            return false;
        }

        return switch (userSaveFollowForm.getType()) {
            case 1 ->
                // 关注
                    saveFollow(userSaveFollowForm, userId);
            case 2 ->
                // 取消关注
                    cancelFollow(userSaveFollowForm, userId);
            default -> false;
        };

    }

    /**
     * 取消关注
     * @param userSaveFollowForm 关注表单
     * @param userId 用户id
     * @return 是否取消关注成功 true:成功 false:失败
     */
	@Transactional(rollbackFor = Exception.class)
    public Boolean cancelFollow(UserSaveFollowForm userSaveFollowForm, long userId) {
        String redisKey = UserRedisConstant.USER_FOLLOW + ":" + userId;

        Double followTime = redisUtil.zScore(redisKey, userSaveFollowForm.getToUserId().toString());
        if (followTime == null) {
            // cache不存在则更新DB
            LambdaQueryWrapper<UserFollowEntity> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(UserFollowEntity::getUserId, userId);
			wrapper.eq(UserFollowEntity::getToUserId, userSaveFollowForm.getToUserId());
            return this.remove(wrapper);
        }

        // cache存在则更新cache和DB
        redisUtil.zRemove(redisKey, userSaveFollowForm.getToUserId().toString());
        LambdaQueryWrapper<UserFollowEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFollowEntity::getUserId, userId);
        wrapper.eq(UserFollowEntity::getToUserId, userSaveFollowForm.getToUserId());
        this.remove(wrapper);

        // 减少用户关注数和粉丝数
        UserFollowEntity entity = UserFollowEntity.builder()
                .userId(userId)
                .toUserId(userSaveFollowForm.getToUserId())
                .build();

        userProfileService.followHandler(entity, false);

        return true;

    }

    /**
     * 保存关注
     * @param userSaveFollowForm 关注表单
     * @param userId 用户id
     * @return 是否关注成功 true:成功 false:失败
     */
    public Boolean saveFollow(UserSaveFollowForm userSaveFollowForm, long userId) {
        String redisKey = UserRedisConstant.USER_FOLLOW + ":" + userId;

        Double followTime = redisUtil.zScore(redisKey, userSaveFollowForm.getToUserId().toString());
        if (followTime != null) {
            // 用户已经关注了
            return false;
        }

        // 查询DB
        LambdaQueryWrapper<UserFollowEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserFollowEntity::getUserId, userId);
        queryWrapper.eq(UserFollowEntity::getToUserId, userSaveFollowForm.getToUserId());
        UserFollowEntity userFollowEntity = this.getOne(queryWrapper);

        if (userFollowEntity != null) {
            // 用户已经关注了
            return false;
        }

        // 构建存储实体
        UserFollowEntity entity = UserFollowEntity.builder()
                .userId(userId)
                .toUserId(userSaveFollowForm.getToUserId())
                .followTime(userSaveFollowForm.getFollowTime())
                .build();

        // 保存关注信息
        userFollowMapper.insert(entity);
        // 增加用户关注数和粉丝数
        userProfileService.followHandler(entity, true);
        // 更新缓存
        redisUtil.zAdd(redisKey, userSaveFollowForm.getToUserId().toString(),
                userSaveFollowForm.getFollowTime().toEpochSecond(ZoneOffset.of("+8")));
        redisUtil.expire(redisKey, 1, TimeUnit.DAYS);

		// 发送事件
		userFollowEventTemplate.sendUserFollowEvent(entity);

        return true;
    }

    /**
     * 查询用户关注列表
     *
     * @param userId 用户id
     * @return 用户关注列表
     */
    @Override
    public List<UserFollowVO> queryUserFollow(Long userId) {

        // 采用穿透缓存的方式查询
        String redisKey = UserRedisConstant.USER_FOLLOW + ":" + userId;

        // 获取zSet中的value和score
        Set<ZSetOperations.TypedTuple<String>> typedTuples = redisUtil.zRangeWithScores(redisKey, 0, -1);
        List<String> userIds = typedTuples
                .stream()
                .map(ZSetOperations.TypedTuple::getValue)
                .collect(Collectors.toList());

        if (CollUtil.isEmpty(userIds)) {
            // 缓存中没有数据，查询DB
            LambdaQueryWrapper<UserFollowEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(UserFollowEntity::getUserId, userId);
            List<UserFollowEntity> userFollowEntities = this.list(queryWrapper);

            if (CollUtil.isNotEmpty(userFollowEntities)) {
                // 将数据放入缓存
                userFollowManagerService.putUserFollowToRedis(userFollowEntities, userId);
            }
            // 包装返回
            return userFollowManagerService.fillUserProfile(userFollowEntities);
        }

        // 缓存中有数据
        List<Long> userIdList = userIds
                .stream()
                .mapToLong(Long::parseLong)
                .boxed()
                .toList();
        List<UserProfileVO> userProfileVOS = userProfileService.batchGetUserProfile(userIdList);
        // 以用户id为key，用户信息为value
        Map<Long, UserProfileVO> userProfileVOMap = userProfileVOS
                .stream()
                .collect(Collectors.toMap(UserProfileVO::getUserId, Function.identity()));

        List<UserFollowVO> result = Lists.newArrayList();
        for (String id : userIds) {
            UserProfileVO userProfileVO = userProfileVOMap.get(Long.parseLong(id));
            if (null != userProfileVO) {
                typedTuples.stream()
                        // 过滤出当前用户的关
                        .filter(typedTuple -> Objects.equals(typedTuple.getValue(), id))
                        .findFirst()
                        .ifPresent(typedTuple -> {
                            UserFollowVO userFollowVO = UserFollowVO.builder()
                                    .userProfile(userProfileVO)
                                    .followTime(LocalDateTimeUtil.of(Objects.requireNonNull(typedTuple.getScore()).longValue() * 1000))
                                    .build();
                            result.add(userFollowVO);
                        });
            }
        }

        return result;

    }

    @Override
    public Boolean queryIsFollow(Long toUserId) {

        long userId = StpUtil.getLoginIdAsLong();

        // 判断是否是关注自己
        if (Objects.equals(userId, toUserId)) {
            return false;
        }

        // 查询缓存
        String redisKey = UserRedisConstant.USER_FOLLOW + ":" + userId;

        // 如果存在该用户的关注数据缓存，则查询缓存
        Boolean hasKey = redisUtil.hasKey(redisKey);

        if (hasKey) {
            // 这里如果存在缓存，则直接以缓存的为准
            Double followTime = redisUtil.zScore(redisKey, toUserId.toString());
            return followTime != null;
        }

        // 缓存中没有该用户的关注数据，则从数据库加载并放入缓存
        LambdaQueryWrapper<UserFollowEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserFollowEntity::getUserId, userId);
        List<UserFollowEntity> userFollowEntities = this.list(queryWrapper);

        if (CollUtil.isNotEmpty(userFollowEntities)) {
            // 将数据放入缓存
            userFollowManagerService.putUserFollowToRedis(userFollowEntities, userId);
            // 遍历列表，判断是否关注了该用户
            for (UserFollowEntity userFollowEntity : userFollowEntities) {
                if (Objects.equals(userFollowEntity.getToUserId(), toUserId)) {
                    return true;
                }
            }
        }

        // 如果缓存中没有该用户的关注数据，且数据库中也没有，则直接返回false
        return false;

    }
}




