package cn.goroute.smart.post.job;

import cn.goroute.smart.post.constant.PostConstant;
import cn.goroute.smart.post.constant.ThumbTypeEnum;
import cn.goroute.smart.post.domain.Thumb;
import cn.goroute.smart.post.service.ThumbService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Alickx
 * @Date: 2022/11/06/16:52
 * @Description: 持久化点赞缓存
 */
@Component
@RequiredArgsConstructor
public class PersistentThumbCache {

	private final StringRedisTemplate redisTemplate;

	private final ThumbService thumbService;


	@XxlJob("persistentPostThumbCache")
	public void persistentPostThumbCache() {

		persistentHandler(PostConstant.Thumb.POST_THUMB_KEY + "*"
				, PostConstant.Thumb.POST_THUMB_EXPIRE, ThumbTypeEnum.POST);

		XxlJobHelper.handleSuccess();
	}


	@XxlJob("persistentCommentThumbCache")
	public void persistentCommentThumbCache() {

		persistentHandler(PostConstant.Thumb.COMMENT_THUMB_KEY + "*"
				, PostConstant.Thumb.COMMENT_THUMB_EXPIRE, ThumbTypeEnum.COMMENT);

		XxlJobHelper.handleSuccess();
	}

	@XxlJob("persistentReplyThumbCache")
	public void persistentReplyThumbCache() {

		persistentHandler(PostConstant.Thumb.REPLY_THUMB_KEY + "*"
				, PostConstant.Thumb.REPLY_THUMB_EXPIRE, ThumbTypeEnum.REPLY);

		XxlJobHelper.handleSuccess();

	}

	/**
	 * 通用持久化点赞缓存逻辑
	 *
	 * @param keyPrefix  key前缀
	 * @param expireTime key中ttl的过期时间
	 * @param thumbTypeEnum 点赞类型
	 */
	private void persistentHandler(String keyPrefix, Long expireTime,ThumbTypeEnum thumbTypeEnum) {
		ScanOptions.ScanOptionsBuilder builder = ScanOptions.scanOptions();
		builder.match(keyPrefix);
		builder.count(1000);
		ScanOptions scanOptions = builder.build();

		List<Thumb> thumbs = Lists.newArrayList();

		try (Cursor<String> cursor = redisTemplate.scan(scanOptions)) {

			Thumb thumb;
			while (cursor.hasNext()) {

				String key = cursor.next();

				// 判断缓存是否过期
				Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
				if (entries.containsKey(PostConstant.Thumb.THUMB_TTL_FIELD)) {
					long ttlNumber = Long.parseLong(entries.get(PostConstant.Thumb.THUMB_TTL_FIELD).toString());
					long expire = LocalDateTimeUtil.now()
							.minusSeconds(expireTime)
							.toEpochSecond(ZoneOffset.of("+8"));

					if (ttlNumber < expire) {
						// 过期则删除缓存
						redisTemplate.delete(key);
						continue;
					}
				}
				Set<Object> field = entries.keySet();
				for (Object o : field) {
					// 判断是否是文章id
					if (!o.toString().matches("[0-9]+")) {
						continue;
					}

					long userId = Long.parseLong(key.split(":")[2]);

					// 判断是否已经持久化
					Thumb checkHasThumb = thumbService.getBaseMapper()
							.selectOne(new LambdaQueryWrapper<Thumb>()
									.eq(Thumb::getUserId, userId)
									.eq(Thumb::getToId, Long.parseLong(o.toString()))
									.eq(Thumb::getType, thumbTypeEnum.getCode()));

					if (checkHasThumb != null) {
						continue;
					}

					thumb = new Thumb();
					thumb.setType(ThumbTypeEnum.POST.getCode());
					thumb.setUserId(userId);
					thumb.setToId(Long.parseLong(o.toString()));
					thumbs.add(thumb);
				}
			}
		}

		if (CollUtil.isNotEmpty(thumbs)) {
			XxlJobHelper.log("用户id:[{}],一共持久化[{}]条点赞记录,持久化类型为:[{}]"
					, thumbs.get(0).getUserId(), thumbs.size(),thumbTypeEnum.getDesc());
			thumbService.saveBatch(thumbs, 500);
		}
	}

}
