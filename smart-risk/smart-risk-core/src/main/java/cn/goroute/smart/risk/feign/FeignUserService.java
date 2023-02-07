package cn.goroute.smart.risk.feign;

import cn.goroute.smart.common.config.FeignConfig;
import cn.goroute.smart.user.domain.UserProfile;
import com.hccake.ballcat.common.model.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "smart-user", configuration = FeignConfig.class)
public interface FeignUserService {


	/**
	 * 根据用户id查询用户信息 - 微服务间调用
	 * @param userId 用户id
	 * @return 用户信息
	 */
	@GetMapping("/user/query/{userId}")
	R<UserProfile> queryUserProfile(@PathVariable("userId") Long userId);

}
