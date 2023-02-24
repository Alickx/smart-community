package cn.goroute.smart.post.modules.thumb.strategy.thumb;

import cn.goroute.smart.post.domain.entity.ThumbEntity;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/10/22/16:40
 * @Description: 点赞策略
 */
public interface ThumbStrategy {

	/**
	 * 点赞
	 * @param thumbEntity 点赞信息
	 * @return 是否点赞成功
	 */
	void saveThumb(ThumbEntity thumbEntity);

	/**
	 * 取消点赞
	 * @param thumbEntity 点赞信息
	 * @return 是否取消点赞成功
	 */
	void cancelThumb(ThumbEntity thumbEntity);

}
