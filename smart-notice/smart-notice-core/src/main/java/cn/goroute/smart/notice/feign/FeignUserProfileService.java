package cn.goroute.smart.notice.feign;

import cn.goroute.smart.common.config.FeignConfig;
import cn.goroute.smart.user.model.dto.UserProfileDTO;
import com.hccake.ballcat.common.model.result.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/09/18/2:03
 * @Description:
 */
@FeignClient(name = "smart-user", configuration = FeignConfig.class)
public interface FeignUserProfileService {

	/**
	 * 获取用户信息
	 *
	 * @return
	 */
	@GetMapping(value = "/user/profile")
	R<UserProfileDTO> getUserProfile(@RequestParam("userId") Long userId);

	/**
	 * 初始化用户信息
	 *
	 * @param userProfileDto 用户信息
	 * @return 是否成功
	 */
	@PostMapping("/user/profile/init")
	R<Boolean> initUserProfile(@RequestBody UserProfileDTO userProfileDto);

	/**
	 * 批量获取用户信息
	 *
	 * @param userIds 用户id列表
	 * @return 用户信息列表
	 */
	@GetMapping("/user/batch/profile")
	@Operation(summary = "批量获取用户信息", description = "批量获取用户信息")
	R<List<UserProfileDTO>> batchGetUserProfile(@RequestParam("userIds") List<Long> userIds);

}
