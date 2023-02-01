package cn.goroute.smart.post.strategy.thumb.impl;

import cn.goroute.smart.post.constant.PostConstant;
import cn.goroute.smart.post.domain.Thumb;
import cn.goroute.smart.post.mapper.CommentMapper;
import cn.goroute.smart.post.strategy.thumb.AbstractThumbStrategy;
import org.springframework.stereotype.Component;

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
	public void saveThumb(Thumb thumb) {

		// 创建缓存
		saveThumbCache(thumb);

		// 更新点赞记录 / 落库逻辑由定时任务保证
		updateThumbDB(thumb);

		// 更新点赞数
		commentMapper.incrThumbNum(thumb.getToId(), 1);

	}

	/**
	 * 取消点赞
	 *
	 * @param thumb 点赞信息
	 * @return 是否取消点赞成功
	 */
	@Override
	public void cancelThumb(Thumb thumb) {

		// 删除缓存
		cancelThumbCache(thumb);

		// 逻辑删除点赞记录
		logicDeleteThumbDB(thumb);

		// 更新点赞数
		commentMapper.descThumbNum(thumb.getToId(), 1);
	}

	@Override
	protected String getThumbCacheKey() {
		return PostConstant.Thumb.COMMENT_THUMB_KEY;
	}
}
