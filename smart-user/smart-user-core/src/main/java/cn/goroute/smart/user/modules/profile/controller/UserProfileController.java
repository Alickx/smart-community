package cn.goroute.smart.user.modules.profile.controller;

import cn.goroute.smart.auth.domain.dto.AuthUserDTO;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.user.domain.entity.UserProfileEntity;
import cn.goroute.smart.user.domain.vo.UserProfileVO;
import cn.goroute.smart.user.modules.profile.service.UserProfileService;
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
	 *
	 * @return 用户信息
	 */
	@GetMapping("/profile")
	public R<UserProfileVO> getUserProfile(@RequestParam(value = "userId", required = false) Long userId) {
		return userProfileService.getUserProfile(userId);
	}

	@PostMapping("/update/profile")
	public R<Boolean> updateUserProfile(@RequestBody UserProfileVO userProfileVO) {
		return userProfileService.updateUserProfile(userProfileVO);
	}

	/**
	 * 批量获取用户信息
	 *
	 * @param userIds 用户id列表
	 * @return 用户信息列表
	 */
	@GetMapping("/batch/profile")
	public R<List<UserProfileVO>> batchGetUserProfile(@RequestParam("userIds") List<Long> userIds) {
		return R.ok(userProfileService.batchGetUserProfile(userIds));
	}


	/**
	 * 初始化用户信息
	 *
	 * @param authUserDTO 用户信息
	 * @return 是否成功
	 */
	@PostMapping("/profile/init")
	public R<Boolean> initUserProfile(@RequestBody AuthUserDTO authUserDTO) {
		return userProfileService.initUserProfile(authUserDTO);
	}

	/**
	 * 根据用户id查询用户信息 - 微服务间调用
	 *
	 * @param userId 用户id
	 * @return 用户信息
	 */
	@GetMapping("/query/{userId}")
	public R<UserProfileEntity> queryUserProfile(@PathVariable("userId") Long userId) {
		return R.ok(userProfileService.getById(userId));
	}

}
