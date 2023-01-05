package cn.goroute.smart.post.manage;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.constant.CommonConstant;
import cn.goroute.smart.common.entity.dto.UserProfileDTO;
import cn.goroute.smart.common.feign.FeignUserProfileService;
import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.post.constant.PostConstant;
import cn.goroute.smart.post.constant.ThumbTypeEnum;
import cn.goroute.smart.post.domain.Thumb;
import cn.goroute.smart.post.mapper.ThumbMapper;
import cn.goroute.smart.post.model.bo.ContentExpansionDTO;
import cn.goroute.smart.post.model.dto.CommentDTO;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.ballcat.common.model.result.SystemResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Alickx
 * @Date: 2022/12/04/15:19
 * @Description:
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentManageService {

	private final ThumbMapper thumbMapper;

	private final FeignUserProfileService feignUserProfileService;

	private final RedisUtil redisUtil;

	private final ThreadLocal<Map<Object,Object>> thumbThreadLocal = new ThreadLocal<>();

	public void fillInfo(List<CommentDTO> records) {

		// 获取用户信息
		this.getUserProfile(records);

		// 获取拓展信息
		this.getCommentExpansion(records);

	}

	private void getCommentExpansion(List<CommentDTO> commentDTOList) {

		// 获取点赞信息
		this.getThumb(commentDTOList);

	}

	private void getThumb(List<CommentDTO> records) {
		boolean login = StpUtil.isLogin();

		String userId = login ? StpUtil.getLoginIdAsString() : null;

		ContentExpansionDTO contentExpansionDTO;

		if (login) {
			// 获取用户点赞缓存
			getUserThumbRecord(StpUtil.getLoginIdAsString());
		}

		// 获取文章id集合
		for (CommentDTO record : records) {
			contentExpansionDTO = new ContentExpansionDTO();
			contentExpansionDTO.init();

			// 判断是否登录
			if (!login) {
				record.setExpansion(contentExpansionDTO);
				continue;
			}
			Long postId = record.getId();

			// 查询点赞信息
			contentExpansionDTO.setIsThumb(checkThumbCache(userId, postId));

			// 判断是否是作者
			contentExpansionDTO.setIsAuthor(record.getUserId().equals(Long.valueOf(userId)));

			record.setExpansion(contentExpansionDTO);

		}

		// 清除缓存，避免内存泄漏
		thumbThreadLocal.remove();
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
					thumbQueryWrapper.eq(Thumb::getType, ThumbTypeEnum.COMMENT.getCode());
					thumbQueryWrapper.eq(Thumb::getDeleted, CommonConstant.NORMAL_STATE);
					Thumb thumb = thumbMapper.selectOne(thumbQueryWrapper);
					return thumb != null;
				}
			} else {
				// 查询db
				LambdaQueryWrapper<Thumb> thumbQueryWrapper = new LambdaQueryWrapper<>();
				thumbQueryWrapper.eq(Thumb::getUserId, userId);
				thumbQueryWrapper.eq(Thumb::getToId, postId);
				thumbQueryWrapper.eq(Thumb::getType, ThumbTypeEnum.COMMENT.getCode());
				thumbQueryWrapper.eq(Thumb::getDeleted, CommonConstant.NORMAL_STATE);
				Thumb thumb = thumbMapper.selectOne(thumbQueryWrapper);
				return thumb != null;
			}
		}
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
		thumbQueryWrapper.eq(Thumb::getType, ThumbTypeEnum.COMMENT.getCode());
		thumbQueryWrapper.eq(Thumb::getDeleted, CommonConstant.NORMAL_STATE);
		thumbQueryWrapper.ge(Thumb::getCreateTime, LocalDateTimeUtil.now().minusSeconds(PostConstant.Thumb.POST_THUMB_EXPIRE));
		thumbQueryWrapper.orderByAsc(Thumb::getToId);
		List<Thumb> thumbs = thumbMapper.selectList(thumbQueryWrapper);
		// 更新点赞记录
		redisUtil.delete(PostConstant.Thumb.COMMENT_THUMB_KEY + userId);
		for (Thumb thumb : thumbs) {
			redisUtil.hPut(PostConstant.Thumb.COMMENT_THUMB_KEY + userId,
					String.valueOf(thumb.getToId()), "1");
		}
		// 更新缓存ttl时间
		redisUtil.hPut(PostConstant.Thumb.COMMENT_THUMB_KEY + userId,
				PostConstant.Thumb.THUMB_TTL_FIELD, String.valueOf(LocalDateTimeUtil.now().toEpochSecond(ZoneOffset.of("+8"))));
		// 更新minCid的值
		if (CollUtil.isNotEmpty(thumbs)) {
			redisUtil.hPut(PostConstant.Thumb.COMMENT_THUMB_KEY + userId,
					PostConstant.Thumb.THUMB_MIN_CID_FIELD, String.valueOf(thumbs.get(0).getToId()));
		}

		// 更新ThreadLocal
		thumbThreadLocal.set(redisUtil.hGetAll(PostConstant.Thumb.COMMENT_THUMB_KEY + userId));
	}

	private void getUserThumbRecord(String userId) {
		// 查询是否点赞
		Map<Object, Object> entries =
				redisUtil.hGetAll(PostConstant.Thumb.COMMENT_THUMB_KEY + userId);

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
					redisUtil.hPut(PostConstant.Thumb.COMMENT_THUMB_KEY + userId,
							PostConstant.Thumb.THUMB_TTL_FIELD, String.valueOf(LocalDateTimeUtil.now().toEpochSecond(ZoneOffset.of("+8"))));
				}
			} else {
				// 缓存已过期，查询数据库
				thumbDbSearchAndCache(userId);
			}
		}
		thumbThreadLocal.set(entries);
	}


	private void getUserProfile(List<CommentDTO> records) {

		if (CollUtil.isNotEmpty(records)) {
			List<Long> userIds = records.stream().map(CommentDTO::getUserId).toList();
			R<List<UserProfileDTO>> resp = feignUserProfileService.batchGetUserProfile(userIds);
			if (resp.getCode() == SystemResultCode.SUCCESS.getCode() && resp.getData() != null) {
				Map<Long, UserProfileDTO> userProfileMap = new HashMap<>();
				for (UserProfileDTO userProfileDTO : resp.getData()) {
					userProfileMap.put(userProfileDTO.getUserId(), userProfileDTO);
				}
				for (CommentDTO commentDTO : records) {
					commentDTO.setUserProfile(userProfileMap.get(commentDTO.getUserId()));
				}
			}
		}
	}


}
