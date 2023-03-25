package cn.goroute.smart.auth.module.login.controller;

import cn.goroute.smart.auth.domain.entity.AuthUserEntity;
import cn.goroute.smart.auth.module.login.service.AuthUserService;
import cn.goroute.smart.common.modules.result.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Alickx
 * @Date: 2023/03/25/16:21
 * @Description:
 */
@RequiredArgsConstructor
@RestController("/auth/admin")
public class AuthUserController {

	private final AuthUserService authUserService;

	@PostMapping("/delete")
	public R<Boolean> delete(@RequestBody AuthUserEntity authUserEntity) {
		return R.ok(authUserService.removeById(authUserEntity.getId()));
	}

}
