package cn.goroute.smart.user.modules.follow.manager;

import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.user.constant.UserRedisConstant;
import cn.goroute.smart.user.domain.entity.UserFollowEntity;
import cn.goroute.smart.user.modules.profile.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

}
