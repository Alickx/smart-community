package cn.goroute.smart.search.feign;


import cn.goroute.smart.common.config.FeignConfig;
import cn.goroute.smart.user.model.dto.UserProfileDTO;
import com.hccake.ballcat.common.model.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "smart-user", configuration = FeignConfig.class)
public interface FeignUserService {


	/**
	 * 批量获取用户信息
	 *
	 * @param userIds 用户id列表
	 * @return 用户信息列表
	 */
	@GetMapping("/user/batch/profile")
	R<List<UserProfileDTO>> batchGetUserProfile(@RequestParam("userIds") List<Long> userIds);

	@GetMapping("/user/profile")
	R<UserProfileDTO> getUserProfile(@RequestParam("userId") Long userId);


}
