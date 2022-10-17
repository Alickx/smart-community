package cn.goroute.smart.post.controller;

import cn.goroute.smart.post.domain.Tag;
import cn.goroute.smart.post.service.TagService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hccake.ballcat.common.model.domain.SelectData;
import com.hccake.ballcat.common.model.result.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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

	@GetMapping("/selectData")
	public R<List<SelectData<Tag>>> selectData() {
		return tagService.getSelectData();
	}

}
