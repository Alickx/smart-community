package cn.goroute.smart.post.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.post.domain.Thumb;
import cn.goroute.smart.post.mq.ThumbMessageTemplate;
import cn.goroute.smart.post.mapper.ThumbMapper;
import cn.goroute.smart.post.service.PostService;
import cn.goroute.smart.post.service.ThumbService;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.springframework.stereotype.Service;

/**
 * @author Alickx
 * @description 针对表【thumb(点赞表)】的数据库操作Service实现
 * @createDate 2022-09-25 16:53:24
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ThumbServiceImpl extends ExtendServiceImpl<ThumbMapper, Thumb>
		implements ThumbService {

	private final PostService postService;


	private final RedisUtil redisUtil;

	private final ThumbMessageTemplate thumbMessageTemplate;

	/**
	 * 保存点赞
	 *
	 * @param thumb 点赞实体类
	 * @return 是否成功
	 */
	@Override
	public R<Boolean> saveThumb(Thumb thumb) {

		String userId = StpUtil.getLoginIdAsString();

		// 发送到消息队列
		SendResult sendResult = thumbMessageTemplate
				.sendPostThumbMessage(Long.valueOf(userId), thumb.getToId(), thumb.getType());

		if (sendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
			return R.ok(true);
		}

		return R.ok(false);

	}

	/**
	 * 取消点赞
	 *
	 * @param thumb 点赞实体类
	 * @return 是否成功
	 */
	@Override
	public R<Boolean> cancelThumb(Thumb thumb) {

		String userId = StpUtil.getLoginIdAsString();

		// TODO 搭建分布式计数服务

		// 发送到消息队列
		thumbMessageTemplate
				.sendPostCancelThumb(Long.valueOf(userId), thumb.getToId(), thumb.getType());

		return R.ok(true);

	}


}




