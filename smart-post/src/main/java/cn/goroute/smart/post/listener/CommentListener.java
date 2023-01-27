package cn.goroute.smart.post.listener;

import cn.goroute.smart.common.util.JsonUtil;
import cn.goroute.smart.post.constant.RocketMqBizConstant;
import cn.goroute.smart.post.domain.Comment;
import cn.goroute.smart.rocketmq.domain.RocketMqEntityMessage;
import cn.goroute.smart.rocketmq.listener.BaseMqMessageListener;
import com.hccake.ballcat.common.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author: Alickx
 * @Date: 2023/01/07/10:56
 * @Description: 评论监听器
 */
@Component
@Slf4j
@RocketMQMessageListener(
		topic = RocketMqBizConstant.Comment.COMMENT_TOPIC,
		consumerGroup = RocketMqBizConstant.Comment.COMMENT_HANDLE_GROUP,
		selectorExpression = RocketMqBizConstant.Comment.COMMENT_HANDLE_TAG,
		consumeThreadNumber = 5
)
public class CommentListener extends BaseMqMessageListener<RocketMqEntityMessage>
		implements RocketMQListener<RocketMqEntityMessage> {


	/**
	 * 消息者名称
	 *
	 * @return 消费者名称
	 */
	@Override
	protected String consumerName() {
		return "帖子评论监听器";
	}

	/**
	 * 消息处理
	 *
	 * @param message 待处理消息
	 */
	@Override
	protected void handleMessage(RocketMqEntityMessage message) {
		SpringUtils.publishEvent(JsonUtil.toObject(message.getMessage(), Comment.class));
	}

	/**
	 * 超过重试次数消息，需要启用isRetry
	 *
	 * @param message 待处理消息
	 */
	@Override
	protected void overMaxRetryTimesMessage(RocketMqEntityMessage message) {
		log.error("帖子评论监听超过重试次数,消息内容:[{}]", JsonUtil.toObject(message.getMessage(), Comment.class).toString());
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
