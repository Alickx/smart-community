package cn.goroute.smart.post.modules.article.controller;

import cn.goroute.smart.post.domain.entity.TagEntity;
import cn.goroute.smart.post.modules.article.service.TagService;
import cn.goroute.smart.common.modules.result.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Alickx
 * @Date: 2022/10/16/14:33
 * @Description: 文章标签
 */
@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

	private final TagService tagService;

	@GetMapping("/query")
	public R<TagEntity> queryTagById(Long id) {
		return R.ok(tagService.getById(id));
	}

}
