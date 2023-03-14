package cn.goroute.smart.user.modules.collect.controller;

import cn.goroute.smart.common.domain.PageParam;
import cn.goroute.smart.common.domain.PageResult;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.post.domain.vo.PostAbbreviationVO;
import cn.goroute.smart.user.modules.collect.service.UserCollectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Alickx
 * @Date: 2023/03/11/21:25
 * @Description: 用户收藏
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/collect")
public class UserCollectController {

	private final UserCollectService userCollectService;

	@GetMapping("/page")
	public R<PageResult<PostAbbreviationVO>> page(PageParam pageParam) {
		return R.ok(userCollectService.queryPage(pageParam));
	}

	@PostMapping("/save")
	public R<Boolean> save(Long postId) {
		return R.ok(userCollectService.saveUserCollect(postId));
	}

	@PostMapping("/delete")
	public R<Boolean> delete(Long postId) {
		return R.ok(userCollectService.deleteUserCollect(postId));
	}

	@GetMapping("/queryIsExist")
	public R<Boolean> isExist(Long postId) {
		return R.ok(userCollectService.isExist(postId));
	}

}
