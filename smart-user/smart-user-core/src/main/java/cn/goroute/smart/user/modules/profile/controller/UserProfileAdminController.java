package cn.goroute.smart.user.modules.profile.controller;

import cn.goroute.smart.common.domain.PageParam;
import cn.goroute.smart.common.domain.PageResult;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.user.domain.entity.UserProfileEntity;
import cn.goroute.smart.user.domain.form.UserProfileQueryForm;
import cn.goroute.smart.user.modules.profile.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2023/03/25/15:53
 * @Description:
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/admin/")
public class UserProfileAdminController {


	private final UserProfileService userProfileService;

	@GetMapping("/page")
	public R<PageResult<UserProfileEntity>> page(@Validated PageParam pageParam, @Validated UserProfileQueryForm form) {
		return R.ok(userProfileService.pageQuery(pageParam, form));
	}

	@PostMapping("/save")
	public R<Boolean> save(@RequestBody UserProfileEntity userProfileEntity) {
		return R.ok(userProfileService.save(userProfileEntity));
	}

	@PostMapping("/update")
	public R<Boolean> update(@RequestBody UserProfileEntity userProfileEntity) {
		return R.ok(userProfileService.updateById(userProfileEntity));
	}

	@PostMapping("/delete")
	public R<Boolean> delete(@RequestBody UserProfileEntity userProfileEntity) {
		return R.ok(userProfileService.removeById(userProfileEntity));
	}

	@PostMapping("/batch/delete")
	public R<Boolean> batchDelete(@RequestBody List<Long> ids) {
		return R.ok(userProfileService.removeBatchByIds(ids));
	}

}
