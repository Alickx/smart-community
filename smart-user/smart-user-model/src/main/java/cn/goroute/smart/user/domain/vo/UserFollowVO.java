package cn.goroute.smart.user.domain.vo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Alickx
 * @Date: 2023/03/05/10:36
 * @Description: 用户关注VO
 */
@Data
@Builder
public class UserFollowVO {

    /**
     * 关注用户信息
     */
    private UserProfileVO userProfile;

    /**
     * 关注时间
     */
    private LocalDateTime followTime;

}
