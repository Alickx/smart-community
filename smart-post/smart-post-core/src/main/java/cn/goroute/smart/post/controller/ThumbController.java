package cn.goroute.smart.post.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.annoation.LogTime;
import cn.goroute.smart.common.constant.enums.ErrorCodeEnum;
import cn.goroute.smart.post.domain.Thumb;
import cn.goroute.smart.post.service.ThumbService;
import com.hccake.ballcat.common.model.result.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/10/21/16:51
 * @Description: 文章点赞
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/thumb")
public class ThumbController {

	private final ThumbService thumbService;

	@PostMapping("/save")
	@LogTime
	public R<Boolean> save(@RequestBody Thumb thumb) {
		if (!StpUtil.isLogin()) {
			return R.failed(ErrorCodeEnum.LOGIN_EXPIRED);
		}
		return thumbService.saveThumb(thumb);
	}

	@PostMapping("/cancel")
	@LogTime
	public R<Boolean> cancel(@RequestBody Thumb thumb) {
		if (!StpUtil.isLogin()) {
			return R.failed(ErrorCodeEnum.LOGIN_EXPIRED);
		}
		return thumbService.cancelThumb(thumb);
	}


}
