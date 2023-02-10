package cn.goroute.smart.post.manager;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.post.constant.UserInteractTypeEnum;
import cn.goroute.smart.post.domain.UserInteract;
import cn.goroute.smart.post.feign.FeignUserProfileService;
import cn.goroute.smart.post.mapper.CommentMapper;
import cn.goroute.smart.post.mapper.ThumbMapper;
import cn.goroute.smart.post.model.dto.CommentDTO;
import cn.goroute.smart.post.model.dto.ContentExpansionDTO;
import cn.goroute.smart.post.service.UserInteractService;
import cn.goroute.smart.user.model.dto.UserProfileDTO;
import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.hccake.ballcat.common.core.constant.enums.BooleanEnum;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.ballcat.common.model.result.SystemResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
public class CommentManagerService {

	private final ThumbMapper thumbMapper;
	private final FeignUserProfileService feignUserProfileService;
	private final CommentMapper commentMapper;
	private final UserInteractService userInteractService;


	public void fillInfo(List<CommentDTO> records) {

		// 获取用户信息
		this.getUserProfile(records);

		// 获取二级回复列表
		this.getPageReplyList(records);

		// 获取拓展信息
		this.getCommentExpansion(records,UserInteractTypeEnum.COMMENT.getCode());

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

			List<CommentDTO> replyRecords = commentDTOPageResult.getRecords();

			// 二级回复查询用户信息
			this.getUserProfile(replyRecords);

			// 二级回复查询拓展信息
			this.getCommentExpansion(replyRecords, UserInteractTypeEnum.REPLY.getCode());

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


	private void getCommentExpansion(List<CommentDTO> records,Integer type) {

		if (CollUtil.isEmpty(records)) {
			return;
		}

		boolean login = StpUtil.isLogin();

		Long userId = login ? StpUtil.getLoginIdAsLong() : null;

		if (!login) {

			for (CommentDTO record : records) {
				ContentExpansionDTO contentExpansionDTO = ContentExpansionDTO.create();
				record.setExpansion(contentExpansionDTO);
			}

			return;
		}

		List<Long> commentIds = records.stream().map(CommentDTO::getId).toList();
		List<UserInteract> userInteractList = userInteractService.batchGetUserPostInteract(commentIds, type, userId);
		Map<Long,UserInteract> userInteractMap = new HashMap<>(userInteractList.size());

		for (UserInteract userInteract : userInteractList) {
			userInteractMap.put(userInteract.getTargetId(),userInteract);
		}

		// 获取文章id集合
		for (CommentDTO record : records) {

			ContentExpansionDTO contentExpansionDTO = ContentExpansionDTO.create();

			UserInteract userInteract = userInteractMap.get(record.getId());
			if (userInteract != null) {
				// 查询点赞信息
				contentExpansionDTO.setIsThumb(userInteract.getIsThumb() == BooleanEnum.TRUE.getValue());
				// 判断是否拥有更多回复
				contentExpansionDTO.setIsMoreReply(this.checkIsMoreReply(record));
				// 判断是否是作者
				contentExpansionDTO.setIsAuthor(record.getUserId().equals(userId));
				record.setExpansion(contentExpansionDTO);
				continue;
			}
			record.setExpansion(contentExpansionDTO);
		}
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
