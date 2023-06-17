package cn.goroute.smart.notice.modules.notice.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.constant.StatusConstant;
import cn.goroute.smart.common.domain.PageParam;
import cn.goroute.smart.common.domain.PageResult;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.notice.constant.enums.MsgTypeEnum;
import cn.goroute.smart.notice.constant.enums.SourceTypeEnum;
import cn.goroute.smart.notice.feign.FeignUserService;
import cn.goroute.smart.notice.modules.notice.converter.NoticeMessageConverter;
import cn.goroute.smart.notice.domain.dto.NoticeCountVO;
import cn.goroute.smart.notice.domain.dto.NoticeMessageDTO;
import cn.goroute.smart.notice.domain.entity.NoticeEntity;
import cn.goroute.smart.notice.domain.entity.NoticeMessageInfoEntity;
import cn.goroute.smart.notice.domain.vo.NoticeMessageVO;
import cn.goroute.smart.notice.modules.notice.manager.NoticeManagerService;
import cn.goroute.smart.notice.modules.notice.mapper.NoticeMapper;
import cn.goroute.smart.notice.modules.notice.service.NoticeService;
import cn.goroute.smart.post.domain.dto.CommentDTO;
import cn.goroute.smart.post.domain.dto.ThumbDTO;
import cn.goroute.smart.user.domain.dto.UserFollowEventDTO;
import cn.goroute.smart.user.domain.vo.UserProfileVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author alickx
 * @description 针对表【t_notice(站内通知)】的数据库操作Service实现
 * @createDate 2023-02-14 14:14:44
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, NoticeEntity>
	implements NoticeService {

	private final NoticeMapper noticeMapper;
	private final NoticeManagerService noticeManagerService;
	private final FeignUserService feignUserService;

	@Override
	public void saveThumbNotice(ThumbDTO thumbDTO) {

		// 查询该点赞通知是否已经存在
		NoticeEntity noticeEntity = noticeMapper.
			queryNoticeIsExist(thumbDTO.getUserId(), thumbDTO.getToUserId(),
				MsgTypeEnum.THUMB.getCode(), thumbDTO.getId());

		if (null == noticeEntity) {

			// 判断是否是自己给自己点赞
			if (thumbDTO.getUserId().equals(thumbDTO.getToUserId())) {
				return;
			}

			// 创建通知
			NoticeMessageInfoEntity noticeMessageInfoEntity = new NoticeMessageInfoEntity();
			noticeMessageInfoEntity.setTargetId(thumbDTO.getId());
			noticeMessageInfoEntity.setPostId(thumbDTO.getPostId());
			noticeMessageInfoEntity.setPostTitle(thumbDTO.getPostTitle());
			noticeMessageInfoEntity.setSourceType(thumbDTO.getType());
			noticeMessageInfoEntity.setContent(thumbDTO.getContent());
			noticeMessageInfoEntity.setMsgType(MsgTypeEnum.THUMB.getCode());

			noticeManagerService.saveNoticeMessage(noticeMessageInfoEntity, thumbDTO.getUserId(), thumbDTO.getToUserId());
		}


	}

	@Override
	public void saveCommentNotice(CommentDTO commentDTO) {

		// 查询该评论通知是否已经存在
		NoticeEntity noticeEntity = noticeMapper.
			queryNoticeIsExist(commentDTO.getUserId(), commentDTO.getToUserId(),
				MsgTypeEnum.COMMENT.getCode(), commentDTO.getId());

		if (null == noticeEntity) {

			// 判断是否是自己给自己评论
			if (commentDTO.getUserId().equals(commentDTO.getToUserId())) {
				return;
			}

			// 创建通知
			NoticeMessageInfoEntity noticeMessageInfoEntity = new NoticeMessageInfoEntity();
			;
			noticeMessageInfoEntity.setTargetId(commentDTO.getId());
			noticeMessageInfoEntity.setPostId(commentDTO.getPostId());
			noticeMessageInfoEntity.setPostTitle(commentDTO.getPostTitle());
			noticeMessageInfoEntity.setSourceType(SourceTypeEnum.COMMENT.getCode());
			noticeMessageInfoEntity.setContent(commentDTO.getContent());
			noticeMessageInfoEntity.setMsgType(MsgTypeEnum.COMMENT.getCode());

			noticeManagerService.saveNoticeMessage(noticeMessageInfoEntity, commentDTO.getUserId(), commentDTO.getToUserId());
		}

	}

	@Override
	public void saveUserFollowNotice(UserFollowEventDTO userFollowEventDTO) {

		// 查询该关注通知是否已经存在
		NoticeEntity noticeEntity = noticeMapper.
			queryNoticeIsExist(userFollowEventDTO.getUserId(), userFollowEventDTO.getToUserId(),
				MsgTypeEnum.FOLLOW.getCode(), userFollowEventDTO.getId());

		if (null == noticeEntity) {

			// 判断是否是自己关注自己
			if (userFollowEventDTO.getUserId().equals(userFollowEventDTO.getToUserId())) {
				return;
			}

			// 创建通知
			NoticeMessageInfoEntity noticeMessageInfoEntity = new NoticeMessageInfoEntity();
			noticeMessageInfoEntity.setTargetId(userFollowEventDTO.getId());
			noticeMessageInfoEntity.setSourceType(SourceTypeEnum.OTHER.getCode());
			noticeMessageInfoEntity.setMsgType(MsgTypeEnum.FOLLOW.getCode());

			noticeManagerService.saveNoticeMessage(noticeMessageInfoEntity, userFollowEventDTO.getUserId(), userFollowEventDTO.getToUserId());
		}

	}

	@Override
	public R<List<NoticeCountVO>> queryNoticeCount() {

		long userId = StpUtil.getLoginIdAsLong();

		List<NoticeCountVO> noticeCountVOS = noticeMapper.queryNoticeCount(userId, StatusConstant.NORMAL_STATUS);

		return R.ok(noticeCountVOS);
	}


	@Override
	public R<PageResult<NoticeMessageVO>> pageNotice(Integer type, PageParam pageParam) {

		long userId = StpUtil.getLoginIdAsLong();


		Page<NoticeMessageDTO> page = new Page<>(pageParam.getPage(), pageParam.getSize());

		IPage<NoticeMessageDTO> pageRecord = noticeMapper.pageNotice(userId, type, page);

		List<NoticeMessageDTO> records = pageRecord.getRecords();

		// 如果集合不为空则查询用户信息
		List<Long> senderIds = records.stream().map(NoticeMessageDTO::getSenderId).distinct().toList();

		R<List<UserProfileVO>> batchResult = feignUserService.batchGetUserProfile(senderIds);

		List<UserProfileVO> userProfileVOS = batchResult.getData();

		// 将用户信息转换为map
		Map<Long, UserProfileVO> userProfileMap = userProfileVOS
			.stream()
			.collect(Collectors.toMap(UserProfileVO::getUserId, Function.identity()));


		// 构造返回结果
		List<NoticeMessageVO> result = records.stream().map(record -> {
			NoticeMessageVO noticeMessageVO = NoticeMessageConverter.INSTANCE.dtoToVO(record);
			UserProfileVO sender = userProfileMap.get(record.getSenderId());
			if (sender != null) {
				noticeMessageVO.setSender(sender);
			}
			return noticeMessageVO;
		}).toList();

		// 获取未读消息的id
		List<Long> ids = records.stream()
			.filter(record -> record.getStatus().equals(StatusConstant.NORMAL_STATUS))
			.map(NoticeMessageDTO::getId).toList();

		// 将未读消息设置为已读
		if (!ids.isEmpty()) {
			noticeManagerService.updateNoticeStatus(ids, StatusConstant.DELETE_STATUS);
		}

		return R.ok(new PageResult<>(result, pageRecord.getTotal()));

	}
}




