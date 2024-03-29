package cn.goroute.smart.post.feign;

import cn.goroute.smart.common.config.FeignConfig;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.user.domain.dto.UserProfileDTO;
import cn.goroute.smart.user.domain.vo.UserProfileVO;
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
public interface FeignUserService {

	/**
	 * 获取用户信息
	 *
	 * @return
	 */
	@GetMapping(value = "/user/profile")
	R<UserProfileVO> getUserProfile(@RequestParam("userId") Long userId);

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
	@GetMapping("/user/profile/batchGet")
	R<List<UserProfileVO>> batchGetUserProfile(@RequestParam("userIds") List<Long> userIds);

}
