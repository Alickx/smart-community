package cn.goroute.smart.post.manage;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.constant.CommonConstant;
import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.post.constant.PostConstant;
import cn.goroute.smart.post.constant.ThumbTypeEnum;
import cn.goroute.smart.post.domain.Thumb;
import cn.goroute.smart.post.feign.FeignUserProfileService;
import cn.goroute.smart.post.mapper.CommentMapper;
import cn.goroute.smart.post.mapper.ThumbMapper;
import cn.goroute.smart.post.model.dto.CommentDTO;
import cn.goroute.smart.post.model.dto.ContentExpansionDTO;
import cn.goroute.smart.user.model.dto.UserProfileDTO;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
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
	private final CommentMapper commentMapper;


	public void fillInfo(List<CommentDTO> records) {

		// 获取用户信息
		this.getUserProfile(records);

		// 获取二级回复列表
		this.getPageReplyList(records);

		// 获取拓展信息
		this.getCommentExpansion(records);

	}

	private void getPageReplyList(List<CommentDTO> records) {

		PageParam pageParam = new PageParam();
		pageParam.setPage(1);
		pageParam.setSize(3);
		PageParam.Sort sort = new PageParam.Sort();
		sort.setField("create_time");
		sort.setAsc(false);
		pageParam.setSorts(Lists.newArrayList(sort));

		records.forEach(record -> {

			// 如果本次查询的评论为二级评论，则不需要查询
			if (record.getFirstCommentId() != null) {
				return;
			}

			// 获取二级回复列表
			PageResult<CommentDTO> commentDTOPageResult = commentMapper
					.queryPageReplyList(pageParam, record.getPostId(), record.getId());

			// 二级回复查询用户信息
			this.getUserProfile(commentDTOPageResult.getRecords());

			// 二级回复查询拓展信息
			this.getCommentExpansion(commentDTOPageResult.getRecords());

			// 填充
			record.setReplyList(commentDTOPageResult);
		});

	}

	/**
	 * 检查是否拥有更多回复
	 * @param commentDTO 评论dto
	 * @return 是否拥有更多回复 true:有 false:没有
	 */
	private Boolean checkIsMoreReply(CommentDTO commentDTO) {
		if (null != commentDTO.getReplyList()) {
			return commentDTO.getReplyList().getTotal() > commentDTO.getReplyList().getRecords().size();
		}
		return false;
	}


	private void getCommentExpansion(List<CommentDTO> records) {

		boolean login = StpUtil.isLogin();

		String userId = login ? StpUtil.getLoginIdAsString() : null;

		if (login) {
			// 获取用户点赞缓存
			getUserThumbRecord(StpUtil.getLoginIdAsString());
		}

		// 获取文章id集合
		for (CommentDTO record : records) {

			ContentExpansionDTO contentExpansionDTO = ContentExpansionDTO.create();

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

			// 判断是否拥有更多回复
			contentExpansionDTO.setIsMoreReply(this.checkIsMoreReply(record));

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
					Thumb thumb = thumbMapper.selectByUserIdAndToIdAndType(Long.valueOf(userId), postId,
							ThumbTypeEnum.COMMENT.getCode());
					return thumb != null;
				}
			} else {
				// 查询db
				Thumb thumb = thumbMapper.selectByUserIdAndToIdAndType(Long.valueOf(userId), postId,
						ThumbTypeEnum.COMMENT.getCode());
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
		// 如果缓存过期，则查询用户一段时间内容的点赞记录
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

	/**
	 * 从缓存中获取用户点赞信息
	 * @param userId 用户id
	 */
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
					.minusSeconds(PostConstant.Thumb.COMMENT_THUMB_EXPIRE).toEpochSecond(ZoneOffset.of("+8"));
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
