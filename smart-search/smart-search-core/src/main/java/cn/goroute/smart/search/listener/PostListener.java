package cn.goroute.smart.search.listener;

import cn.goroute.smart.common.constant.RocketMqBizConstant;
import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.rocketmq.domain.RocketMqEntityMessage;
import cn.goroute.smart.rocketmq.listener.BaseMqMessageListener;
import com.hccake.ballcat.common.util.JsonUtils;
import com.hccake.ballcat.common.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author: Alickx
 * @Date: 2023/01/31/12:20
 * @Description:
 */
@Slf4j
@Component
@RocketMQMessageListener(
		topic = RocketMqBizConstant.Post.POST_TOPIC,
		consumerGroup = RocketMqBizConstant.Post.POST_HANDLE_GROUP,
		selectorExpression = RocketMqBizConstant.Post.POST_HANDLE_TAG,
		consumeThreadNumber = 5
)
public class PostListener extends BaseMqMessageListener<RocketMqEntityMessage>
		implements RocketMQListener<RocketMqEntityMessage> {


	/**
	 * 消息者名称
	 *
	 * @return 消费者名称
	 */
	@Override
	protected String consumerName() {
		return "文章发布事件监听者";
	}

	/**
	 * 消息处理
	 *
	 * @param message 待处理消息
	 */
	@Override
	protected void handleMessage(RocketMqEntityMessage message) {
		SpringUtils.publishEvent(JsonUtils.toObj(message.getMessage(), Post.class));
	}

	/**
	 * 超过重试次数消息，需要启用isRetry
	 *
	 * @param message 待处理消息
	 */
	@Override
	protected void overMaxRetryTimesMessage(RocketMqEntityMessage message) {
		log.error("文章发布事件监听者超过最大重试次数，消息内容：[{}]", JsonUtils.toJson(message));
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
