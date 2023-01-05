package cn.goroute.smart.post.listener;

import cn.goroute.smart.common.util.JsonUtil;
import cn.goroute.smart.post.constant.RocketMqBizConstant;
import cn.goroute.smart.post.domain.Thumb;
import cn.goroute.smart.post.strategy.thumb.ThumbStrategy;
import cn.goroute.smart.post.strategy.thumb.ThumbStrategyEnum;
import cn.goroute.smart.rocketmq.domain.RocketMqEntityMessage;
import cn.goroute.smart.rocketmq.listener.BaseMqMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/10/22/16:00
 * @Description: 点赞业务监听者
 */
@Component
@Slf4j
@RocketMQMessageListener(
		topic = RocketMqBizConstant.Thumb.THUMB_TOPIC,
		consumerGroup = RocketMqBizConstant.Thumb.THUMB_HANDLE_GROUP,
		selectorExpression = RocketMqBizConstant.Thumb.THUMB_HANDLE_TAG,
		consumeThreadNumber = 10
)
public class ThumbListener extends BaseMqMessageListener<RocketMqEntityMessage >
		implements RocketMQListener<RocketMqEntityMessage > {
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
		// 执行点赞策略
		Thumb thumb = JsonUtil.toObject(message.getMessage(), Thumb.class);
		ThumbStrategy strategy = ThumbStrategyEnum.getStrategyByType(thumb.getType());
		if (strategy == null) {
			log.error("点赞业务监听者未找到对应的点赞策略:[{}]", JsonUtil.toObject(message.getMessage(), Thumb.class).toString());
			return;
		}
		if (message.getLogicFlag()) {
			strategy.saveThumb(thumb);
		} else {
			strategy.cancelThumb(thumb);
		}
	}

	/**
	 * 超过重试次数消息，需要启用isRetry
	 *
	 * @param message 待处理消息
	 */
	@Override
	protected void overMaxRetryTimesMessage(RocketMqEntityMessage message) {
		log.error("超过重试次数,消息内容:[{}]", JsonUtil.toObject(message.getMessage(), Thumb.class).toString());
	}

	/**
	 * 是否异常时重复发送
	 *
	 * @return true: 消息重试，false：不重试
	 */
	@Override
	protected boolean isRetry() {
		return true;
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
