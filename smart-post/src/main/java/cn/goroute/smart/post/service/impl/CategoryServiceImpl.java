package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.post.domain.Category;
import cn.goroute.smart.post.domain.CategoryTag;
import cn.goroute.smart.post.domain.Tag;
import cn.goroute.smart.post.mapper.CategoryMapper;
import cn.goroute.smart.post.service.CategoryService;
import cn.goroute.smart.post.service.CategoryTagService;
import cn.goroute.smart.post.service.TagService;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hccake.ballcat.common.model.domain.SelectData;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.ballcat.common.redis.core.annotation.CachePut;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Alickx
* @description 针对表【category(板块表)】的数据库操作Service实现
* @createDate 2022-09-25 16:53:24
*/
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends ExtendServiceImpl<CategoryMapper, Category>
    implements CategoryService{

	private final TagService tagService;

	private final CategoryTagService categoryTagService;

	/**
	 * 获取板块下拉数据及其标签数据
	 *
	 * @return 板块下拉数据及其标签数据
	 */
	@Override
	@CachePut(key = "category:selectData")
	public R<List<SelectData<List<SelectData<Tag>>>>> getSelectData() {

		List<Category> list = this.list();

		// 获取板块信息
		List<SelectData<List<SelectData<Tag>>>> selectDataList = list.stream().map(category -> {
			SelectData<List<SelectData<Tag>>> categorySelectData = new SelectData<>();
			categorySelectData.setName(category.getName());
			categorySelectData.setValue(category.getId());

			// 查询对应标签
			BaseMapper<CategoryTag> categoryTagBaseMapper = categoryTagService.getBaseMapper();
			List<CategoryTag> categoryTagList = categoryTagBaseMapper
					.selectList(new LambdaQueryWrapper<CategoryTag>()
							.eq(CategoryTag::getCategoryId, category.getId()));

			// 获取标签信息
			List<Long> tagIdList = categoryTagList
					.stream()
					.map(CategoryTag::getTagId).collect(Collectors.toList());

			if (tagIdList.isEmpty()) {
				categorySelectData.setExtendObj(ListUtil.empty());
				return categorySelectData;
			}

			List<SelectData<Tag>> tagSelectDataList  = tagService
					.listByIds(tagIdList)
					.stream()
					.map(tag -> {
						SelectData<Tag> tagSelectData = new SelectData<>();
						tagSelectData.setName(tag.getContent());
						tagSelectData.setValue(tag.getId());
						return tagSelectData;
					}).collect(Collectors.toList());

			categorySelectData.setExtendObj(tagSelectDataList);
			return categorySelectData;

		}).collect(Collectors.toList());

		return R.ok(selectDataList);
	}
}




