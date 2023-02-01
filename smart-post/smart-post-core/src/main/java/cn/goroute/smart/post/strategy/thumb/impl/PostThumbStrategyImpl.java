package cn.goroute.smart.post.strategy.thumb.impl;

import cn.goroute.smart.post.constant.PostConstant;
import cn.goroute.smart.post.domain.Thumb;
import cn.goroute.smart.post.mapper.PostMapper;
import cn.goroute.smart.post.strategy.thumb.AbstractThumbStrategy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/10/22/16:41
 * @Description:
 */
@Component("postThumbStrategy")
public class PostThumbStrategyImpl extends AbstractThumbStrategy {


	@Resource
	private PostMapper postMapper;

	/**
	 * 点赞
	 *
	 * @param thumb 点赞信息
	 */
	@Override
	public void saveThumb(Thumb thumb) {

		long toId = thumb.getToId();

		// 创建缓存
		saveThumbCache(thumb);

		// 保存点赞记录
		updateThumbDB(thumb);

		// 更新文章点赞数
		postMapper.incrThumbNum(toId, 1);
	}

	/**
	 * 取消点赞
	 *
	 * @param thumb 点赞信息
	 */
	@Override
	public void cancelThumb(Thumb thumb) {

		long toId = thumb.getToId();

		// 删除缓存
		cancelThumbCache(thumb);

		// 逻辑删除记录
		logicDeleteThumbDB(thumb);

		// 更新文章点赞数
		postMapper.descThumbNum(toId,1);
	}

	@Override
	protected String getThumbCacheKey() {
		return PostConstant.Thumb.POST_THUMB_KEY;
	}
}
