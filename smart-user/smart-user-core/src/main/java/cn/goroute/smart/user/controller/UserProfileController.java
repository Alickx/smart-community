package cn.goroute.smart.user.controller;

import cn.goroute.smart.common.annoation.LogTime;
import cn.goroute.smart.user.domain.UserProfile;
import cn.goroute.smart.user.model.dto.UserProfileDTO;
import cn.goroute.smart.user.model.vo.UserProfileVO;
import cn.goroute.smart.user.service.UserProfileService;
import com.hccake.ballcat.common.model.result.R;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public R<UserProfileDTO> getUserProfile(@RequestParam("userId") Long userId) {
        return userProfileService.getUserProfile(userId);
    }

	@PostMapping("/update/profile")
	@Operation(summary = "更新用户信息", description = "更新用户信息")
	public R<Boolean> updateUserProfile(@RequestBody UserProfileVO userProfileVO) {
		return userProfileService.updateUserProfile(userProfileVO);
	}

	/**
	 * 批量获取用户信息
	 * @param userIds 用户id列表
	 * @return 用户信息列表
	 */
	@GetMapping("/batch/profile")
	@Operation(summary = "批量获取用户信息", description = "批量获取用户信息")
	@LogTime
	public R<List<UserProfileDTO>> batchGetUserProfile(@RequestParam("userIds") List<Long> userIds) {
		return userProfileService.batchGetUserProfile(userIds);
	}


    /**
     * 初始化用户信息
     * @param userProfileDto 用户信息
     * @return 是否成功
     */
    @PostMapping("/profile/init")
    @Operation(summary = "初始化用户信息", description = "初始化用户信息")
    public R<Boolean> initUserProfile(@RequestBody UserProfileDTO userProfileDto){
        return userProfileService.initUserProfile(userProfileDto);
    }

	/**
	 * 根据用户id查询用户信息 - 微服务间调用
	 * @param userId 用户id
	 * @return 用户信息
	 */
	@GetMapping("/query/{userId}")
	@Operation(summary = "根据用户id查询用户信息", description = "微服务间调用")
	public R<UserProfile> queryUserProfile(@PathVariable("userId") Long userId) {
		return R.ok(userProfileService.getById(userId));
	}

}
