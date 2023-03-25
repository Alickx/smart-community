package cn.goroute.smart.user.modules.follow.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.goroute.smart.common.domain.PageParam;
import cn.goroute.smart.common.domain.PageResult;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.user.domain.form.UserSaveFollowForm;
import cn.goroute.smart.user.domain.vo.UserFollowVO;
import cn.goroute.smart.user.modules.follow.service.UserFollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: Alickx
 * @Date: 2023/03/05/9:51
 * @Description: 用户关注
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/follow")
public class UserFollowController {

	private final UserFollowService userFollowService;

	/**
	 * 用户关注操作 关注和取消关注
	 * @param userSaveFollowForm 用户关注信息
	 * @return 关注结果
	 */
	@PostMapping("/saveFollow")
	@SaCheckLogin
	public R<Boolean> saveFollow(@RequestBody @Valid UserSaveFollowForm userSaveFollowForm) {
		return R.ok(userFollowService.saveFollow(userSaveFollowForm));
	}

	/**
	 * 查询用户关注列表
	 * @param userId 用户id
	 * @return 用户关注列表
	 */
	@GetMapping("/queryUserFollow")
	public R<PageResult<UserFollowVO>> queryUserFollow(PageParam pageParam, Long userId) {
		return R.ok(userFollowService.queryUserFollow(pageParam,userId));
	}

	/**
	 * 查询是否关注
	 * @param toUserId 被关注用户id
	 * @return 是否关注 true:已关注 false:未关注
	 */
	@GetMapping("/queryIsFollow")
	@SaCheckLogin
	public R<Boolean> queryIsFollow(@RequestParam("toUserId") Long toUserId) {
		return R.ok(userFollowService.queryIsFollow(toUserId));
	}

	@GetMapping("/queryFollowerByPage")
	public R<PageResult<UserFollowVO>> queryFollowerByPage(PageParam pageParam, Long userId) {
		return R.ok(userFollowService.queryFansByPage(pageParam,userId));
	}


}
