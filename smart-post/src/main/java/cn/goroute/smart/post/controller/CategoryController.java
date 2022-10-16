package cn.goroute.smart.post.controller;

import cn.goroute.smart.post.domain.Category;
import cn.goroute.smart.post.service.CategoryService;
import com.hccake.ballcat.common.model.domain.SelectData;
import com.hccake.ballcat.common.model.result.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
	public R<List<SelectData<Category>>> selectData() {
		List<Category> list =
				categoryService.list();

		List<SelectData<Category>> selectDataList = list.stream().map(category -> {
			SelectData<Category> selectData = new SelectData<>();
			selectData.setName(category.getName());
			selectData.setValue(category.getId());
			return selectData;
		}).collect(Collectors.toList());

		return R.ok(selectDataList);
	}

}
