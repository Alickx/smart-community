package cn.goroute.smart.post.controller;

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
	public R<Boolean> save(@RequestBody Long postId) {
		return thumbService.saveThumb(postId);
	}


}
