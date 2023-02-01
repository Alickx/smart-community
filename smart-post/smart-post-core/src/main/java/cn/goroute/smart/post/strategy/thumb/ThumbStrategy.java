package cn.goroute.smart.post.strategy.thumb;

import cn.goroute.smart.post.domain.Thumb;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/10/22/16:40
 * @Description: 点赞策略
 */
public interface ThumbStrategy {

	/**
	 * 点赞
	 * @param thumb 点赞信息
	 * @return 是否点赞成功
	 */
	void saveThumb(Thumb thumb);

	/**
	 * 取消点赞
	 * @param thumb 点赞信息
	 * @return 是否取消点赞成功
	 */
	void cancelThumb(Thumb thumb);

}
