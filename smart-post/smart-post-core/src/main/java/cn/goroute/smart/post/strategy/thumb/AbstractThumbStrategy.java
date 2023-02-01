package cn.goroute.smart.post.strategy.thumb;

import cn.goroute.smart.common.constant.CommonConstant;
import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.post.constant.PostConstant;
import cn.goroute.smart.post.domain.Thumb;
import cn.goroute.smart.post.service.ThumbService;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.ZoneOffset;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/10/22/16:41
 * @Description: 点赞策略抽象类
 */
@Component
public abstract class AbstractThumbStrategy implements ThumbStrategy {

	@Resource
	protected RedisUtil redisUtil;

	@Resource
	protected ThumbService thumbService;

	protected abstract String getThumbCacheKey();

	/**
	 * 保存点赞缓存
	 * @param thumb 点赞信息
	 */
	protected void saveThumbCache(Thumb thumb) {

		Long userId = thumb.getUserId();
		Long toId = thumb.getToId();

		// 设置缓存过期字段和文章点赞状态
		redisUtil.hPut(getThumbCacheKey() + userId, String.valueOf(toId), "1");
		redisUtil.hPut(getThumbCacheKey() + userId, PostConstant.Thumb.THUMB_TTL_FIELD,
				String.valueOf(LocalDateTimeUtil.now().toEpochSecond(ZoneOffset.of("+8"))));
	}

	/**
	 * 删除点赞缓存
	 * @param thumb 点赞信息
	 */
	protected void cancelThumbCache(Thumb thumb) {

		Long userId = thumb.getUserId();
		Long toId = thumb.getToId();

		// 设置缓存过期字段和文章点赞状态
		redisUtil.hDelete(getThumbCacheKey() + userId, String.valueOf(toId));
		redisUtil.hPut(getThumbCacheKey() + userId, PostConstant.Thumb.THUMB_TTL_FIELD,
				String.valueOf(LocalDateTimeUtil.now().toEpochSecond(ZoneOffset.of("+8"))));
	}

	/**
	 * 逻辑删除点赞记录
	 * @param thumb 点赞信息
	 */
	protected void logicDeleteThumbDB(Thumb thumb) {
		// 删除点赞记录
		thumb.setDeleted(CommonConstant.DELETE_STATE);
		thumbService.getBaseMapper()
				.update(thumb,new LambdaUpdateWrapper<Thumb>()
						.eq(Thumb::getUserId, thumb.getUserId())
						.eq(Thumb::getToId, thumb.getToId())
						.eq(Thumb::getType, thumb.getType()));
	}

	/**
	 * 更新点赞记录
	 * @param thumb 点赞信息
	 */
	protected void updateThumbDB(Thumb thumb) {

		thumb.setDeleted(CommonConstant.NORMAL_STATE);
		thumbService.getBaseMapper()
				.update(thumb,new LambdaUpdateWrapper<Thumb>()
						.eq(Thumb::getUserId, thumb.getUserId())
						.eq(Thumb::getToId, thumb.getToId())
						.eq(Thumb::getType, thumb.getType()));
	}




}
