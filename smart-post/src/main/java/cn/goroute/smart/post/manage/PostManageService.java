package cn.goroute.smart.post.manage;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.constant.CommonConstant;
import cn.goroute.smart.common.model.dto.UserProfileDTO;
import cn.goroute.smart.common.feign.FeignUserProfileService;
import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.post.constant.PostConstant;
import cn.goroute.smart.post.constant.ThumbTypeEnum;
import cn.goroute.smart.post.converter.CategoryConverter;
import cn.goroute.smart.post.converter.TagConverter;
import cn.goroute.smart.post.domain.*;
import cn.goroute.smart.post.mapper.*;
import cn.goroute.smart.post.model.dto.ContentExpansionDTO;
import cn.goroute.smart.post.model.dto.CategoryDTO;
import cn.goroute.smart.post.model.dto.PostBaseDTO;
import cn.goroute.smart.post.model.dto.TagDTO;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.ballcat.common.model.result.SystemResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/09/27/11:01
 * @Description: 文章服务
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PostManageService {

	private final FeignUserProfileService feignUserProfileService;

	private final CategoryMapper categoryMapper;

	private final TagMapper tagMapper;

	private final ThumbMapper thumbMapper;

	private final CommentMapper commentMapper;

	private final RedisUtil redisUtil;

	private final PostMapper postMapper;

	private final ThreadLocal<Map<Object,Object>> thumbThreadLocal = new ThreadLocal<>();

	private List<UserProfileDTO> batchGetUserProfile(List<Long> userIds) {
		R<List<UserProfileDTO>> userProfile = feignUserProfileService.batchGetUserProfile(userIds);
		return userProfile.getData();
	}

	/**
	 * 补充文章作者，板块和标签信息
	 *
	 * @param records 文章列表
	 * @return 补充后的文章列表
	 */
	public List<? extends PostBaseDTO> fillInfo(List<? extends PostBaseDTO> records) {

		// 补充作者信息
		fillAuthor(records);

		// 补充板块信息
		fillCategory(records);

		// 补充点赞信息和收藏信息
		fillExpansion(records);

		// 补充标签信息
		fillTag(records);

		return records;
	}

	/**
	 * 补充文章标签信息
	 *
	 * @param records 文章列表
	 */
	private void fillTag(List<? extends PostBaseDTO> records) {
		for (PostBaseDTO record : records) {
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
	private void fillAuthor(List<? extends PostBaseDTO> records) {
		if (CollUtil.isNotEmpty(records)) {
			List<Long> userIds = records.stream().map(PostBaseDTO::getAuthorId).toList();
			R<List<UserProfileDTO>> resp = feignUserProfileService.batchGetUserProfile(userIds);
			if (resp.getCode() == SystemResultCode.SUCCESS.getCode() && resp.getData() != null) {
				Map<Long, UserProfileDTO> userProfileMap = new HashMap<>();
				for (UserProfileDTO userProfileDTO : resp.getData()) {
					userProfileMap.put(userProfileDTO.getUserId(), userProfileDTO);
				}
				for (PostBaseDTO postBaseDTO : records) {
					postBaseDTO.setAuthor(userProfileMap.get(postBaseDTO.getAuthorId()));
				}
			}
		}
	}

	/**
	 * 补充文章板块信息
	 *
	 * @param records 文章列表
	 */
	private void fillCategory(List<? extends PostBaseDTO> records) {
		List<Long> categoryIds = records
				.stream()
				.map(PostBaseDTO::getCategoryId)
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
	 * 补充文章拓展信息
	 *
	 * @param records 文章列表
	 */
	private void fillExpansion(List<? extends PostBaseDTO> records) {

		boolean login = StpUtil.isLogin();

		String userId = login ? StpUtil.getLoginIdAsString() : null;

		ContentExpansionDTO contentExpansionDTO;

		if (login) {
			// 获取用户点赞缓存
			getUserThumbRecord(StpUtil.getLoginIdAsString());
		}

		// 获取文章id集合
		for (PostBaseDTO record : records) {
			contentExpansionDTO = new ContentExpansionDTO();

			// 判断是否登录
			if (!login) {
				contentExpansionDTO.setIsThumb(false);
				contentExpansionDTO.setIsComment(false);
				contentExpansionDTO.setIsAuthor(false);
				record.setExpansion(contentExpansionDTO);
				continue;
			}
			Long postId = record.getId();

			// 查询点赞信息
			contentExpansionDTO.setIsThumb(checkThumbCache(userId, postId));

			// 查询是否评论
			contentExpansionDTO.setIsComment(getIsComment(userId,postId));

			// 判断是否是作者
			contentExpansionDTO.setIsAuthor(record.getAuthorId().equals(Long.valueOf(userId)));

			record.setExpansion(contentExpansionDTO);

		}

		// 清除缓存，避免内存泄漏
		thumbThreadLocal.remove();

	}

	private Boolean getIsComment(String userId, Long postId) {
		//TODO 查询评论
		LambdaQueryWrapper<Comment> commentQueryWrapper;
		commentQueryWrapper = new LambdaQueryWrapper<>();
		commentQueryWrapper.eq(Comment::getUserId, userId);
		commentQueryWrapper.eq(Comment::getPostId, postId);
		List<Comment> comments = commentMapper.selectList(commentQueryWrapper);
		return CollUtil.isNotEmpty(comments);
	}


	private void getUserThumbRecord(String userId) {
		// 查询是否点赞
		Map<Object, Object> entries =
				redisUtil.hGetAll(PostConstant.Thumb.POST_THUMB_KEY + userId);

		// 判断是否存在点赞hash缓存
		if (entries.isEmpty()) {
			thumbDbSearchAndCache(userId);
		} else {
			long ttl = Long.parseLong((String) entries.get(PostConstant.Thumb.THUMB_TTL_FIELD));
			// 判断是否过期
			// 当前时间戳 - 过期时间 < ttl，证明还未过期
			long expire = LocalDateTimeUtil.now()
					.minusSeconds(PostConstant.Thumb.POST_THUMB_EXPIRE).toEpochSecond(ZoneOffset.of("+8"));
			if (expire < ttl) {
				// 判断是否需要更新缓存时间,如果小于创建/更新时间戳的1/3，则更新缓存时间
				if ((ttl - expire) < ttl / 3) {
					redisUtil.hPut(PostConstant.Thumb.POST_THUMB_KEY + userId,
							PostConstant.Thumb.THUMB_TTL_FIELD, String.valueOf(LocalDateTimeUtil.now().toEpochSecond(ZoneOffset.of("+8"))));
				}
			} else {
				// 缓存已过期，查询数据库
				thumbDbSearchAndCache(userId);
			}
		}

		thumbThreadLocal.set(entries);
	}

	/**
	 * 数据库查询点赞信息并更新写入缓存
	 *
	 * @param userId 用户id
	 */
	private void thumbDbSearchAndCache(String userId) {
		// 如果过期，则查询用户一段时间内容的点赞记录
		LambdaQueryWrapper<Thumb>  thumbQueryWrapper = new LambdaQueryWrapper<>();
		thumbQueryWrapper.eq(Thumb::getUserId, userId);
		thumbQueryWrapper.eq(Thumb::getType, ThumbTypeEnum.POST.getCode());
		thumbQueryWrapper.eq(Thumb::getDeleted, CommonConstant.NORMAL_STATE);
		thumbQueryWrapper.ge(Thumb::getCreateTime, LocalDateTimeUtil.now().minusSeconds(PostConstant.Thumb.POST_THUMB_EXPIRE));
		thumbQueryWrapper.orderByAsc(Thumb::getToId);
		List<Thumb> thumbs = thumbMapper.selectList(thumbQueryWrapper);
		// 更新点赞记录
		redisUtil.delete(PostConstant.Thumb.POST_THUMB_KEY + userId);
		for (Thumb thumb : thumbs) {
			redisUtil.hPut(PostConstant.Thumb.POST_THUMB_KEY + userId,
					String.valueOf(thumb.getToId()), "1");
		}
		// 更新缓存ttl时间
		redisUtil.hPut(PostConstant.Thumb.POST_THUMB_KEY + userId,
				PostConstant.Thumb.THUMB_TTL_FIELD, String.valueOf(LocalDateTimeUtil.now().toEpochSecond(ZoneOffset.of("+8"))));
		// 更新minCid的值
		if (CollUtil.isNotEmpty(thumbs)) {
			redisUtil.hPut(PostConstant.Thumb.POST_THUMB_KEY + userId,
					PostConstant.Thumb.THUMB_MIN_CID_FIELD, String.valueOf(thumbs.get(0).getToId()));
		}

		// 更新ThreadLocal
		thumbThreadLocal.set(redisUtil.hGetAll(PostConstant.Thumb.POST_THUMB_KEY + userId));
	}

	/**
	 * 检查文章id是否在缓存中
	 *
	 * @param userId  用户id
	 * @param postId  文章id
	 * @return 是否点赞 true:点赞 false:未点赞
	 */
	@NotNull
	private Boolean checkThumbCache(String userId, Long postId) {

		Map<Object, Object> entries = thumbThreadLocal.get();

		// 查询文章id是否在缓存中
		if (entries.containsKey(String.valueOf(postId))) {
			return true;
		} else {
			// 如果文章id不在缓存中，则查询文章id是否小于缓存中的文章最小值
			if (entries.containsKey(PostConstant.Thumb.THUMB_MIN_CID_FIELD)) {
				Long minCid = Long.parseLong((String) entries.get(PostConstant.Thumb.THUMB_MIN_CID_FIELD));
				if (postId > minCid) {
					// 如果文章id大于缓存中的文章最小值，则未点赞
					return false;
				} else {
					// 查询db
					LambdaQueryWrapper<Thumb> thumbQueryWrapper = new LambdaQueryWrapper<>();
					thumbQueryWrapper.eq(Thumb::getUserId, userId);
					thumbQueryWrapper.eq(Thumb::getToId, postId);
					thumbQueryWrapper.eq(Thumb::getType, ThumbTypeEnum.POST.getCode());
					thumbQueryWrapper.eq(Thumb::getDeleted, CommonConstant.NORMAL_STATE);
					Thumb thumb = thumbMapper.selectOne(thumbQueryWrapper);
					return thumb != null;
				}
			} else {
				// 查询db
				LambdaQueryWrapper<Thumb> thumbQueryWrapper = new LambdaQueryWrapper<>();
				thumbQueryWrapper.eq(Thumb::getUserId, userId);
				thumbQueryWrapper.eq(Thumb::getToId, postId);
				thumbQueryWrapper.eq(Thumb::getType, ThumbTypeEnum.POST.getCode());
				thumbQueryWrapper.eq(Thumb::getDeleted, CommonConstant.NORMAL_STATE);
				Thumb thumb = thumbMapper.selectOne(thumbQueryWrapper);
				return thumb != null;
			}
		}
	}


	/**
	 * 保存文章到数据库
	 * @param post 文章实体类
	 */
	@Transactional(rollbackFor = Exception.class)
	public void savePost2Db(Post post) {

		postMapper.insert(post);

	}

}
