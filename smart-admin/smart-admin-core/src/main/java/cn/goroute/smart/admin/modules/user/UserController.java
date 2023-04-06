package cn.goroute.smart.admin.modules.user;

import cn.goroute.smart.common.domain.PageParam;
import cn.goroute.smart.common.domain.PageResult;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.user.domain.api.UserService;
import cn.goroute.smart.user.domain.entity.UserProfileEntity;
import cn.goroute.smart.user.domain.form.UserProfileQueryForm;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Alickx
 * @Date: 2023/03/25/16:43
 * @Description:
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	private final

	@GetMapping("/page")
	public R<PageResult<UserProfileEntity>> page(@Validated PageParam pageParam, UserProfileQueryForm form) {
		return userService.page(pageParam,form);
	}

	@PostMapping("/update")
	public R<Boolean> update(@RequestBody UserProfileEntity userProfileEntity) {
		return userService.update(userProfileEntity);
	}


	@PostMapping("/delete")
	public R<Boolean> delete(@RequestBody UserProfileEntity userProfileEntity) {

		R<Boolean> result = userService.delete(userProfileEntity);
		if (result.getCode() == 200 && result.getData()) {
			authUserService
		}

	}

}
