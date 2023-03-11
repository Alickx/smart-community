package cn.goroute.smart.notice.modules.notice.mq.listener.post;

import cn.goroute.smart.common.constant.MqBizConstant;
import cn.goroute.smart.notice.modules.notice.service.NoticeService;
import cn.goroute.smart.post.domain.dto.ThumbDTO;
import cn.goroute.smart.post.domain.entity.ThumbEntity;
import cn.goroute.smart.rocketmq.domain.RocketMqEntityMessage;
import cn.goroute.smart.rocketmq.listener.BaseMqMessageListener;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/10/22/16:00
 * @Description: 点赞业务监听者
 */
@RequiredArgsConstructor
@Component
@Slf4j
@RocketMQMessageListener(
		topic = MqBizConstant.ThumbMqConstant.THUMB_TOPIC,
		consumerGroup = MqBizConstant.ThumbMqConstant.THUMB_NOTICE_GROUP,
		consumeThreadNumber = 5
)
public class PostThumbListener extends BaseMqMessageListener<RocketMqEntityMessage>
		implements RocketMQListener<RocketMqEntityMessage>{
	private final NoticeService noticeService;

	/**
	 * 消息者名称
	 *
	 * @return 消费者名称
	 */
	@Override
	protected String consumerName() {
		return "点赞业务监听者";
	}

	/**
	 * 消息处理
	 *
	 * @param message 待处理消息
	 */
	@Override
	protected void handleMessage(RocketMqEntityMessage message) {
		// 创建通知
		ThumbDTO thumbDTO = JSON.parseObject(message.getMessage(), ThumbDTO.class);
		noticeService.saveThumbNotice(thumbDTO);
	}

	/**
	 * 超过重试次数消息，需要启用isRetry
	 *
	 * @param message 待处理消息
	 */
	@Override
	protected void overMaxRetryTimesMessage(RocketMqEntityMessage message) {
		log.error("帖子评论监听超过重试次数,消息内容:[{}]", JSON.parseObject(message.getMessage(), ThumbEntity.class).toString());
	}

	/**
	 * 是否异常时重复发送
	 *
	 * @return true: 消息重试，false：不重试
	 */
	@Override
	protected boolean isRetry() {
		return false;
	}

	/**
	 * 消费异常时是否抛出异常
	 *
	 * @return true: 抛出异常，false：消费异常(如果没有开启重试则消息会被自动ack)
	 */
	@Override
	protected boolean isThrowException() {
		return true;
	}

	@Override
	public void onMessage(RocketMqEntityMessage rocketMqEntityMessage) {
		super.dispatchMessage(rocketMqEntityMessage);
	}
}
