package cn.goroute.smart.notice.manager;

import cn.goroute.smart.notice.mapper.NoticeMapper;
import cn.goroute.smart.notice.mapper.NoticeMessageInfoMapper;
import cn.goroute.smart.notice.domain.entity.NoticeEntity;
import cn.goroute.smart.notice.domain.entity.NoticeMessageInfoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Alickx
 * @Date: 2023/02/14 16:00:27
 * @Description:
 */
@Service
@RequiredArgsConstructor
public class NoticeManagerService {


	private final NoticeMapper noticeMapper;

	private final NoticeMessageInfoMapper noticeMessageInfoMapper;


	@Transactional(rollbackFor = Exception.class)
	public void saveNoticeMessage(NoticeMessageInfoEntity noticeMessageInfoEntity, Long senderId, Long receiveId){

		noticeMessageInfoMapper.insert(noticeMessageInfoEntity);

		NoticeEntity noticeEntity = new NoticeEntity();
		noticeEntity.setSenderId(senderId);
		noticeEntity.setReceiverId(receiveId);
		noticeEntity.setMsgInfoId(noticeMessageInfoEntity.getId());

		noticeMapper.insert(noticeEntity);
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateNoticeStatus(List<Long> ids, Integer deleteStatus) {
		noticeMapper.updateNoticeStatus(ids, deleteStatus);
	}
}
