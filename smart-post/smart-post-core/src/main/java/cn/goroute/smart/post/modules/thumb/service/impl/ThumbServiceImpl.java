package cn.goroute.smart.post.modules.thumb.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.modules.result.R;
import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.post.constant.PostRedisConstant;
import cn.goroute.smart.post.modules.thumb.async.ThumbAsyncService;
import cn.goroute.smart.post.domain.entity.ThumbEntity;
import cn.goroute.smart.post.domain.form.ThumbForm;
import cn.goroute.smart.post.modules.thumb.mapper.ThumbMapper;
import cn.goroute.smart.post.modules.thumb.service.ThumbService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Alickx
 * @description 针对表【thumb(点赞表)】的数据库操作Service实现
 * @createDate 2022-09-25 16:53:24
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ThumbServiceImpl extends ServiceImpl<ThumbMapper, ThumbEntity>
		implements ThumbService {

	private final ThumbAsyncService thumbAsyncService;
	private final RedisUtil redisUtil;

	/**
	 * 保存点赞
	 *
	 * @param thumbForm 点赞请求
	 * @return 是否成功
	 */
	@Override
	public R<Boolean> saveThumb(ThumbForm thumbForm) {

		Long userId = StpUtil.getLoginIdAsLong();

		thumbAsyncService.thumbHandle(thumbForm,userId,true);

		return R.ok(true);

	}

	/**
	 * 取消点赞
	 *
	 * @param thumbForm 点赞请求
	 * @return 是否成功
	 */
	@Override
	public R<Boolean> cancelThumb(ThumbForm thumbForm) {

		Long userId = StpUtil.getLoginIdAsLong();

		thumbAsyncService.thumbHandle(thumbForm,userId,false);

		return R.ok(true);

	}

	@Override
	public Long queryPostThumbCountByPostId(Long postId) {

		// 从缓存中获取
		String value = (String) redisUtil
			.hGet(PostRedisConstant.PostKey.POST_THUMB_COUNT_KEY, postId.toString());

		if (StrUtil.isNotBlank(value)) {
			return Long.valueOf(value);
		}

		// 缓存中没有，从数据库中获取
		// TODO 宽表查询

		return 0L;


	}


}




