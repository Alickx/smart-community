package cn.goroute.smart.auth.service;

import cn.goroute.smart.common.config.FeignInterceptor;
import cn.goroute.smart.common.entity.dto.UserProfileDto;
import com.hccake.ballcat.common.model.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Alickx
 * @Date: 2022/09/18/2:03
 * @Description:
 */
@FeignClient(name = "smart-gateway"
        , path = "/api/v1/user"
        ,configuration = FeignInterceptor.class)
public interface FeignUserProfileService {

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping(value = "/profile")
    R<UserProfileDto> getUserProfile(@RequestParam("userId") Long userId);

    /**
     * 初始化用户信息
     * @param userProfileDto 用户信息
     * @return 是否成功
     */
    @PostMapping("/profile/init")
    R<Boolean> initUserProfile(@RequestBody UserProfileDto userProfileDto);

}
