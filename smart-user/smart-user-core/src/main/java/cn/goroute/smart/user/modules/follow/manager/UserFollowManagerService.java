package cn.goroute.smart.user.modules.follow.manager;

import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.user.constant.UserRedisConstant;
import cn.goroute.smart.user.domain.entity.UserFollowEntity;
import cn.goroute.smart.user.domain.vo.UserFollowVO;
import cn.goroute.smart.user.domain.vo.UserProfileVO;
import cn.goroute.smart.user.modules.profile.service.UserProfileService;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: Alickx
 * @Date: 2023/03/05/10:41
 * @Description:
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserFollowManagerService {
    private final RedisUtil redisUtil;
    private final UserProfileService userProfileService;

    /**
     * 将用户关注信息存入redis
     *
     * @param userFollowEntities 用户关注信息
     * @param userId             用户id
     */
    public void putUserFollowToRedis(List<UserFollowEntity> userFollowEntities, Long userId) {

        String redisKey = UserRedisConstant.USER_FOLLOW + ":" + userId;

        for (UserFollowEntity userFollowEntity : userFollowEntities) {

            Long toUserId = userFollowEntity.getToUserId();
            LocalDateTime followTime = userFollowEntity.getFollowTime();

            redisUtil.zAdd(redisKey, toUserId.toString(), followTime.toEpochSecond(ZoneOffset.ofHours(8)));
        }

        // 给Key设置过期时间 6小时
        redisUtil.expire(redisKey, 6, TimeUnit.HOURS);

    }

    /**
     * 填充用户关注信息
     *
     * @param userFollowEntities 用户关注信息
     */
    public List<UserFollowVO> fillUserProfile(List<UserFollowEntity> userFollowEntities) {

        List<Long> userIds = userFollowEntities.stream().map(UserFollowEntity::getToUserId).toList();

        List<UserProfileVO> userProfileVOS = userProfileService.batchGetUserProfile(userIds);

        Map<Long, UserProfileVO> profileVOMap = userProfileVOS
                .stream()
                .collect(Collectors.toMap(UserProfileVO::getUserId, Function.identity()));

        // 包装返回信息
        List<UserFollowVO> list = Lists.newArrayList();
        for (UserFollowEntity userFollowEntity : userFollowEntities) {
            Long toUserId = userFollowEntity.getToUserId();
            UserProfileVO userProfileVO = profileVOMap.get(toUserId);
            UserFollowVO userFollowVO = UserFollowVO.builder()
                    .userProfile(userProfileVO)
                    .followTime(userFollowEntity.getFollowTime())
                    .build();
            list.add(userFollowVO);
        }

        return list;

    }

}
