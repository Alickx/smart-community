package cn.goroute.smart.auth.domain.api;

import cn.goroute.smart.auth.domain.entity.AuthUserEntity;
import cn.goroute.smart.common.config.FeignConfig;
import cn.goroute.smart.common.modules.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: Alickx
 * @Date: 2023/03/29/23:44
 * @Description:
 */
@FeignClient(value = "smart-auth", configuration = FeignConfig.class)
public interface AuthUserService {

	@PostMapping("/delete")
	R<Boolean> delete(@RequestBody AuthUserEntity authUserEntity);

}
