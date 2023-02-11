package cn.goroute.smart.post.strategy.thumb;

import cn.goroute.smart.common.constant.StateConstant;
import cn.goroute.smart.post.domain.Thumb;
import cn.goroute.smart.post.mapper.ThumbMapper;
import cn.goroute.smart.post.service.UserInteractService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/10/22/16:41
 * @Description: 点赞策略抽象类
 */
@Component
public abstract class AbstractThumbStrategy implements ThumbStrategy {

	@Resource
	protected ThumbMapper thumbMapper;

	@Resource
	protected UserInteractService userInteractService;


	/**
	 * 更新点赞记录
	 * @param thumb 点赞信息
	 */
	protected void saveThumb2DB(Thumb thumb) {

		Thumb thumbEntity = thumbMapper.selectById(thumb.getId());
		if (thumbEntity == null) {
			thumbMapper.insert(thumb);
		} else {
			thumb.setDeleted(StateConstant.NORMAL_STATE);
			thumbMapper.updateById(thumb);
		}
	}


	/**
	 * 检查是否已经点赞
	 * @param thumb 点赞信息
	 * @return true：已经点赞，false：未点赞
	 */
	protected boolean checkIsThumb(Thumb thumb) {

		Long userId = thumb.getUserId();
		Long toId = thumb.getToId();

		// 检查数据库中是否已经点赞
		Thumb thumbResult = thumbMapper.selectByUserIdAndToIdAndType(userId, toId, thumb.getType());

		return thumbResult != null;

	}




}
