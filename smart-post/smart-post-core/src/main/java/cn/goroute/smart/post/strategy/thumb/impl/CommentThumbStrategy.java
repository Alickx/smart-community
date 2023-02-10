package cn.goroute.smart.post.strategy.thumb.impl;

import cn.goroute.smart.post.domain.Thumb;
import cn.goroute.smart.post.mapper.CommentMapper;
import cn.goroute.smart.post.strategy.thumb.AbstractThumbStrategy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author: Alickx
 * @Date: 2023/01/04/18:21
 * @Description: 评论点赞策略
 */
@Component
public class CommentThumbStrategy extends AbstractThumbStrategy {
	@Resource
	private CommentMapper commentMapper;

	/**
	 * 点赞
	 *
	 * @param thumb 点赞信息
	 * @return 是否点赞成功
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveThumb(Thumb thumb) {

		// 保存或更新点赞记录
		saveThumb2DB(thumb);

		// 更新点赞数
		commentMapper.incrThumbNum(thumb.getToId(), 1);

		// 保存/更新用户关系
		userInteractService.updateThumbUserRelation(thumb, true);

	}

	/**
	 * 取消点赞
	 *
	 * @param thumb 点赞信息
	 * @return 是否取消点赞成功
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void cancelThumb(Thumb thumb) {

		// 逻辑删除点赞记录
		thumbMapper.deleteById(thumb);

		// 更新点赞数
		commentMapper.descThumbNum(thumb.getToId(), 1);

		// 保存/更新用户关系
		userInteractService.updateThumbUserRelation(thumb, false);
	}
}
