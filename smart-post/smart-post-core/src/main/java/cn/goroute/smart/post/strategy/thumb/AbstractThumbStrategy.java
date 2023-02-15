package cn.goroute.smart.post.strategy.thumb;

import cn.goroute.smart.common.constant.StatusConstant;
import cn.goroute.smart.post.domain.dto.ThumbDTO;
import cn.goroute.smart.post.domain.entity.ThumbEntity;
import cn.goroute.smart.post.mapper.PostMapper;
import cn.goroute.smart.post.mapper.ThumbMapper;
import cn.goroute.smart.post.mq.ThumbSaveOrUpdateEventMessageTemplate;
import cn.goroute.smart.post.service.UserInteractService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/10/22/16:41
 * @Description: 点赞策略抽象类
 */
@Component
public abstract class AbstractThumbStrategy implements ThumbStrategy {
	@Resource
	protected ThumbSaveOrUpdateEventMessageTemplate thumbSaveOrUpdateEventMessageTemplate;
	@Resource
	protected ThumbMapper thumbMapper;
	@Resource
	protected UserInteractService userInteractService;
	@Resource
	protected PostMapper postMapper;


	protected void sendThumbMqEvent(ThumbDTO thumbDTO, Boolean saveFlag) {
		thumbSaveOrUpdateEventMessageTemplate.sendPostThumbMessage(thumbDTO, saveFlag);
	}

	/**
	 * 保存点赞记录
	 * @param thumbEntity 点赞实体
	 * @return 是否是新点赞 true：是，false：否
	 */
	protected Boolean saveThumbRecord(ThumbEntity thumbEntity) {

		ThumbEntity thumb = thumbMapper.selectById(thumbEntity.getId());
		if (thumb == null) {
			thumbMapper.insert(thumbEntity);
			return true;
		} else {
			if (Objects.equals(thumb.getDeleted(), StatusConstant.NORMAL_STATUS)) {
				return false;
			}
			thumb.setDeleted(StatusConstant.NORMAL_STATUS);
			thumbMapper.updateById(thumb);
			return false;
		}

	}

	/**
	 * 检查是否已经点赞
	 *
	 * @param thumbEntity 点赞信息
	 * @return true：已经点赞，false：未点赞
	 */
	protected boolean checkIsThumb(ThumbEntity thumbEntity) {

		Long userId = thumbEntity.getUserId();
		Long toId = thumbEntity.getToId();

		// 检查数据库中是否已经点赞
		ThumbEntity thumbEntityResult = thumbMapper.selectByUserIdAndToIdAndType(userId, toId, thumbEntity.getType());

		return thumbEntityResult != null;

	}

}
