package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.post.constant.PostRedisConstant;
import cn.goroute.smart.post.domain.entity.CategoryEntity;
import cn.goroute.smart.post.domain.entity.CategoryTagEntity;
import cn.goroute.smart.post.domain.entity.TagEntity;
import cn.goroute.smart.post.mapper.CategoryMapper;
import cn.goroute.smart.post.service.CategoryService;
import cn.goroute.smart.post.service.CategoryTagService;
import cn.goroute.smart.post.service.TagService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hccake.ballcat.common.model.domain.SelectData;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.ballcat.common.redis.core.annotation.Cached;
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
public class CategoryServiceImpl extends ExtendServiceImpl<CategoryMapper, CategoryEntity>
    implements CategoryService{

	private final TagService tagService;

	private final CategoryTagService categoryTagService;
	private final RedisUtil redisUtil;

	/**
	 * 获取板块下拉数据及其标签数据
	 *
	 * @return 板块下拉数据及其标签数据
	 */
	@Override
	@Cached(key = "CATEGORY:SELECT_DATA", ttl = 60 * 60 * 24)
	public R<List<SelectData<List<SelectData<TagEntity>>>>> getSelectData() {

		List<CategoryEntity> list = this.list();

		// 获取板块信息
		List<SelectData<List<SelectData<TagEntity>>>> selectDataList = list.stream().map(category -> {
			SelectData<List<SelectData<TagEntity>>> categorySelectData = new SelectData<>();
			categorySelectData.setName(category.getName());
			categorySelectData.setValue(category.getId());

			// 查询对应标签
			BaseMapper<CategoryTagEntity> categoryTagBaseMapper = categoryTagService.getBaseMapper();
			List<CategoryTagEntity> categoryTagEntityList = categoryTagBaseMapper
					.selectList(new LambdaQueryWrapper<CategoryTagEntity>()
							.eq(CategoryTagEntity::getCategoryId, category.getId()));

			// 获取标签信息
			List<Long> tagIdList = categoryTagEntityList
					.stream()
					.map(CategoryTagEntity::getTagId).collect(Collectors.toList());

			if (tagIdList.isEmpty()) {
				categorySelectData.setAttributes(ListUtil.empty());
				return categorySelectData;
			}

			List<SelectData<TagEntity>> tagSelectDataList  = tagService
					.listByIds(tagIdList)
					.stream()
					.map(tag -> {
						SelectData<TagEntity> tagSelectData = new SelectData<>();
						tagSelectData.setName(tag.getContent());
						tagSelectData.setValue(tag.getId());
						return tagSelectData;
					}).collect(Collectors.toList());

			categorySelectData.setAttributes(tagSelectDataList);
			return categorySelectData;

		}).collect(Collectors.toList());

		return R.ok(selectDataList);
	}

	@Override
	public CategoryEntity queryByName(String categoryName) {

		List<CategoryEntity> categoryEntityList = this.getCategoryList();

		if (CollUtil.isEmpty(categoryEntityList)) {
			return null;
		}

		for (CategoryEntity categoryEntity : categoryEntityList) {
			if (StrUtil.equals(categoryEntity.getName(), categoryName)) {
				return categoryEntity;
			}
		}

		return null;

	}

	@Override
	@Cached(key = PostRedisConstant.CategoryKey.POST_CATEGORY_KEY, ttl = 60 * 60 * 24)
	public List<CategoryEntity> getCategoryList() {
		return this.list();
	}

	@Override
	public CategoryEntity queryById(Long categoryId) {

		List<CategoryEntity> categoryEntityList = this.getCategoryList();

		for (CategoryEntity categoryEntity : categoryEntityList) {
			if (categoryEntity.getId().equals(categoryId)) {
				return categoryEntity;
			}
		}

		return null;

	}
}




