package cn.goroute.smart.user.controller;

import cn.goroute.smart.common.entity.dto.UserProfileDto;
import cn.goroute.smart.user.service.UserProfileService;
import com.hccake.ballcat.common.model.result.R;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Alickx
 * @Date: 2022/09/18/15:58
 * @Description: 用户信息控制器
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserProfileController {

    private final UserProfileService userProfileService;

    /**
     * 获取用户信息
     * @return 用户信息
     */
    @GetMapping("/profile")
    @Operation(summary = "获取用户信息", description = "获取用户信息")
    public R<UserProfileDto> getUserProfile(@RequestParam("token") String token) {
        return userProfileService.getUserProfile(token);
    }

    /**
     * 初始化用户信息
     * @param userProfileDto 用户信息
     * @return 是否成功
     */
    @PostMapping("/profile/init")
    @Operation(summary = "初始化用户信息", description = "初始化用户信息")
    public R<Boolean> initUserProfile(@RequestBody UserProfileDto userProfileDto){
        return userProfileService.initUserProfile(userProfileDto);
    }

}
