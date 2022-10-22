package cn.goroute.smart.post.strategy.thumb.impl;

import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.post.constant.PostConstant;
import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.post.domain.Thumb;
import cn.goroute.smart.post.service.PostService;
import cn.goroute.smart.post.service.ThumbService;
import cn.goroute.smart.post.strategy.thumb.AbstractThumbStrategy;
import cn.hutool.core.date.LocalDateTimeUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.ZoneOffset;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/10/22/16:41
 * @Description:
 */
@Component("postThumbStrategy")
public class PostThumbStrategyImpl extends AbstractThumbStrategy {

	@Resource
	private ThumbService thumbService;

	@Resource
	private PostService postService;

	@Resource
	private RedisUtil redisUtil;

	/**
	 * 点赞
	 *
	 * @param thumb 点赞信息
	 * @return 是否点赞成功
	 */
	@Override
	public void saveThumb(Thumb thumb) {

		Long userId = thumb.getUserId();
		Long toId = thumb.getToId();

		// 设置缓存过期字段和文章点赞状态
		redisUtil.hPut(PostConstant.Thumb.POST_THUMB_KEY + userId, String.valueOf(toId), "1");
		redisUtil.hPut(PostConstant.Thumb.POST_THUMB_KEY + userId, PostConstant.Thumb.POST_THUMB_TTL,
				String.valueOf(LocalDateTimeUtil.now().toEpochSecond(ZoneOffset.of("+8"))));

		// 先保存点赞记录
		thumbService.save(thumb);

		// 再更新文章点赞数
		Post post = postService.getById(toId);
		post.setThumbCount(post.getThumbCount() + 1);
		postService.updateById(post);
	}

	/**
	 * 取消点赞
	 *
	 * @param thumb 点赞信息
	 * @return 是否取消点赞成功
	 */
	@Override
	public void cancelThumb(Thumb thumb) {

		Long userId = thumb.getUserId();

		Long toId = thumb.getToId();

		// 设置缓存过期字段和文章点赞状态
		redisUtil.hDelete(PostConstant.Thumb.POST_THUMB_KEY + userId, String.valueOf(toId));
		redisUtil.hPut(PostConstant.Thumb.POST_THUMB_KEY + userId, PostConstant.Thumb.POST_THUMB_TTL,
				String.valueOf(LocalDateTimeUtil.now().toEpochSecond(ZoneOffset.of("+8"))));


		// 先删除点赞记录
		Long postId = thumb.getToId();
		thumbService.cancelThumb(thumb);

		// 再更新文章点赞数
		Post post = postService.getById(postId);
		post.setThumbCount(post.getThumbCount() - 1);
		postService.updateById(post);
	}
}
