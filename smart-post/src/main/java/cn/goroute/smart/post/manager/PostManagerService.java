package cn.goroute.smart.post.manager;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.entity.dto.UserProfileDTO;
import cn.goroute.smart.common.feign.FeignUserProfileService;
import cn.goroute.smart.post.converter.CategoryConverter;
import cn.goroute.smart.post.converter.TagConverter;
import cn.goroute.smart.post.domain.*;
import cn.goroute.smart.post.entity.bo.PostExpansionBO;
import cn.goroute.smart.post.entity.dto.CategoryDTO;
import cn.goroute.smart.post.entity.dto.PostDTO;
import cn.goroute.smart.post.entity.dto.TagDTO;
import cn.goroute.smart.post.service.*;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.extend.mybatis.plus.conditions.query.LambdaQueryWrapperX;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/09/27/11:01
 * @Description: 文章服务
 */
@Service
@RequiredArgsConstructor
public class PostManagerService {

	private final FeignUserProfileService feignUserProfileService;

	private final CategoryService categoryService;

	private final TagService tagService;

	private final PostTagService postTagService;

	private final ThumbService thumbService;

	private final CommentService commentService;


	public UserProfileDTO getUserProfile(Long userId) {
		R<UserProfileDTO> userProfile = feignUserProfileService.getUserProfile(userId);
		return userProfile.getData();
	}

	public List<UserProfileDTO> batchGetUserProfile(List<Long> userIds) {
		R<List<UserProfileDTO>> userProfile = feignUserProfileService.batchGetUserProfile(userIds);
		return userProfile.getData();
	}

	/**
	 * 补充文章作者，板块和标签信息
	 *
	 * @param records 文章列表
	 * @return 补充后的文章列表
	 */
	public List<PostDTO> supplementaryPostInformation(List<PostDTO> records) {

		// 补充作者信息
		supplementaryAuthor(records);

		// 补充板块信息
		supplementaryCategory(records);

		// 补充标签信息
		supplementaryTag(records);

		// 补充点赞信息和收藏信息
		supplementaryExpansion(records);

		return records;
	}

	/**
	 * 补充标签信息
	 *
	 * @param records 文章列表
	 */
	private void supplementaryTag(List<PostDTO> records) {
		// 获取文章id集合
		List<Long> postIds = records.stream().map(PostDTO::getId).collect(Collectors.toList());

		for (Long postId : postIds) {

			// 获取文章标签id集合
			LambdaQueryWrapperX<PostTag> postTagQueryWrapper = new LambdaQueryWrapperX<>();
			postTagQueryWrapper.eq(PostTag::getPostId, postId);
			List<PostTag> postTagDTOList = postTagService.getBaseMapper().selectList(postTagQueryWrapper);
			List<Long> tagIds = postTagDTOList
					.stream()
					.map(PostTag::getTagId)
					.collect(Collectors.toList());

			// 获取标签信息
			List<Tag> tags = tagService.listByIds(tagIds);
			List<TagDTO> tagDtoS = TagConverter.INSTANCE.poToDTO(tags);

			// 补充标签信息
			for (PostDTO postDTO : records) {
				if (postDTO.getId().equals(postId)) {
					postDTO.setTags(tagDtoS);
				}
			}

		}

	}

	/**
	 * 补充文章作者信息
	 *
	 * @param records 文章列表
	 */
	private void supplementaryAuthor(List<PostDTO> records) {
		List<Long> authorIds = records
				.stream()
				.map(PostDTO::getAuthorId).collect(Collectors.toList());
		List<UserProfileDTO> userProfileDTOList = this.batchGetUserProfile(authorIds);

		records.forEach(postDTO -> userProfileDTOList.forEach(userProfileDTO -> {
			if (postDTO.getAuthorId().equals(userProfileDTO.getUserId())) {
				postDTO.setAuthor(userProfileDTO);
			}
		}));
	}

	/**
	 * 补充文章板块信息
	 *
	 * @param records 文章列表
	 */
	private void supplementaryCategory(List<PostDTO> records) {
		List<Long> categoryIds = records
				.stream()
				.map(PostDTO::getCategoryId)
				.collect(Collectors.toList());

		List<Category> categoryList = categoryService.listByIds(categoryIds);
		List<CategoryDTO> categoryDTOList = CategoryConverter.INSTANCE.poToDTO(categoryList);

		records.forEach(postDTO -> categoryDTOList.forEach(categoryDTO -> {
			if (postDTO.getCategoryId().equals(categoryDTO.getCategoryId())) {
				postDTO.setCategory(categoryDTO);
			}
		}));
	}

	/**
	 * 补充文章点赞信息和收藏信息
	 * @param records 文章列表
	 */
	private void supplementaryExpansion(List<PostDTO> records) {

		if (!StpUtil.isLogin()) {
			return;
		}

		long userId = StpUtil.getLoginIdAsLong();

		PostExpansionBO postExpansionBO;
		LambdaQueryWrapperX<Thumb> thumbQueryWrapper;
		LambdaQueryWrapperX<Comment> commentQueryWrapper;
		// 获取文章id集合
		for (PostDTO record : records) {
			// 查询是否点赞
			postExpansionBO = new PostExpansionBO();
			thumbQueryWrapper = new LambdaQueryWrapperX<>();
			thumbQueryWrapper.eq(Thumb::getUserId, userId);
			thumbQueryWrapper.eq(Thumb::getPostId, record.getId());
			Thumb thumb = thumbService.getBaseMapper().selectOne(thumbQueryWrapper);
			postExpansionBO.setIsThumb(thumb != null);

			// 查询是否评论
			commentQueryWrapper = new LambdaQueryWrapperX<>();
			commentQueryWrapper.eq(Comment::getUserId, userId);
			commentQueryWrapper.eq(Comment::getPostId, record.getId());
			Comment comment = commentService.getBaseMapper().selectOne(commentQueryWrapper);
			postExpansionBO.setIsComment(comment != null);

			record.setExpansion(postExpansionBO);

		}

	}

}
