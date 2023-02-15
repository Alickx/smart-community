package cn.goroute.smart.post.controller;

import cn.goroute.smart.post.domain.entity.CategoryEntity;
import cn.goroute.smart.post.domain.entity.TagEntity;
import cn.goroute.smart.post.service.CategoryService;
import com.hccake.ballcat.common.model.domain.SelectData;
import com.hccake.ballcat.common.model.result.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/10/16/14:38
 * @Description: 文章板块
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping("/selectData")
	public R<List<SelectData<List<SelectData<TagEntity>>>>> selectData() {
		return categoryService.getSelectData();
	}


	@GetMapping("/query")
	public R<CategoryEntity> queryCategoryById(Long id) {
		return R.ok(categoryService.getById(id));
	}
}
