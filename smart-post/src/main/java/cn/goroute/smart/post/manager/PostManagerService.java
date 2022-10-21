package cn.goroute.smart.post.manager;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.entity.dto.UserProfileDTO;
import cn.goroute.smart.common.feign.FeignUserProfileService;
import cn.goroute.smart.post.converter.CategoryConverter;
import cn.goroute.smart.post.converter.TagConverter;
import cn.goroute.smart.post.domain.Category;
import cn.goroute.smart.post.domain.Comment;
import cn.goroute.smart.post.domain.Tag;
import cn.goroute.smart.post.domain.Thumb;
import cn.goroute.smart.post.entity.bo.PostExpansionBO;
import cn.goroute.smart.post.entity.dto.CategoryDTO;
import cn.goroute.smart.post.entity.dto.PostAbbreviationDTO;
import cn.goroute.smart.post.entity.dto.TagDTO;
import cn.goroute.smart.post.mapper.CategoryMapper;
import cn.goroute.smart.post.mapper.CommentMapper;
import cn.goroute.smart.post.mapper.TagMapper;
import cn.goroute.smart.post.mapper.ThumbMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hccake.ballcat.common.model.result.R;
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

	private final CategoryMapper categoryMapper;

	private final TagMapper tagMapper;

	private final ThumbMapper thumbMapper;

	private final CommentMapper commentMapper;


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
	public List<PostAbbreviationDTO> supplementaryPostInformation(List<PostAbbreviationDTO> records) {

		//TODO 使用异步多线程优化

		// 补充作者信息
		supplementaryAuthor(records);

		// 补充板块信息
		supplementaryCategory(records);

		// 补充点赞信息和收藏信息
		supplementaryExpansion(records);

		// 补充标签信息
		supplementaryTag(records);

		return records;
	}

	/**
	 * 补充文章标签信息
	 * @param records 文章列表
	 */
	private void supplementaryTag(List<PostAbbreviationDTO> records) {
		for (PostAbbreviationDTO record : records) {
			Tag tag = tagMapper.selectById(record.getTagId());
			TagDTO tagDTO = TagConverter.INSTANCE.poToDTO(tag);
			record.setTag(tagDTO);
		}
	}

	/**
	 * 补充文章作者信息
	 *
	 * @param records 文章列表
	 */
	private void supplementaryAuthor(List<PostAbbreviationDTO> records) {
		List<Long> authorIds = records
				.stream()
				.map(PostAbbreviationDTO::getAuthorId).collect(Collectors.toList());
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
	private void supplementaryCategory(List<PostAbbreviationDTO> records) {
		List<Long> categoryIds = records
				.stream()
				.map(PostAbbreviationDTO::getCategoryId)
				.collect(Collectors.toList());

		List<Category> categoryList = categoryMapper.selectBatchIds(categoryIds);
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
	private void supplementaryExpansion(List<PostAbbreviationDTO> records) {

		if (!StpUtil.isLogin()) {
			return;
		}

		long userId = StpUtil.getLoginIdAsLong();

		PostExpansionBO postExpansionBO;
		LambdaQueryWrapper<Thumb> thumbQueryWrapper;
		LambdaQueryWrapper<Comment> commentQueryWrapper;
		// 获取文章id集合
		for (PostAbbreviationDTO record : records) {
			// 查询是否点赞
			postExpansionBO = new PostExpansionBO();
			thumbQueryWrapper = new LambdaQueryWrapper<>();
			thumbQueryWrapper.eq(Thumb::getUserId, userId);
			thumbQueryWrapper.eq(Thumb::getPostId, record.getId());
			Thumb thumb = thumbMapper.selectOne(thumbQueryWrapper);
			postExpansionBO.setIsThumb(thumb != null);

			// 查询是否评论
			commentQueryWrapper = new LambdaQueryWrapper<>();
			commentQueryWrapper.eq(Comment::getUserId, userId);
			commentQueryWrapper.eq(Comment::getPostId, record.getId());
			Comment comment = commentMapper.selectOne(commentQueryWrapper);
			postExpansionBO.setIsComment(comment != null);

			record.setExpansion(postExpansionBO);

		}

	}

}
