package cn.goroute.smart.post.strategy.thumb.impl;

import cn.goroute.smart.post.domain.Thumb;
import cn.goroute.smart.post.mapper.PostMapper;
import cn.goroute.smart.post.strategy.thumb.AbstractThumbStrategy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional(rollbackFor = Exception.class)
	public void saveThumb(Thumb thumb) {

		long toId = thumb.getToId();

		// 判断是否已经点赞
		if (!checkIsThumb(thumb)) {

			// 保存点赞记录
			saveThumb2DB(thumb);

			// 更新文章点赞数
			postMapper.incrThumbNum(toId, 1);

			// 保存/更新用户关系
			userInteractService.updateThumbUserRelation(thumb, true);
		}
	}


	/**
	 * 取消点赞
	 *
	 * @param thumb 点赞信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void cancelThumb(Thumb thumb) {

		long toId = thumb.getToId();

		// 逻辑删除记录
		thumbMapper.deleteById(thumb);

		// 更新文章点赞数
		postMapper.descThumbNum(toId,1);

		// 保存/更新用户关系
		userInteractService.updateThumbUserRelation(thumb, false);
	}
}
