package cn.goroute.smart.post.modules.article.mq.listener;

import cn.goroute.smart.common.constant.MqBizConstant;
import cn.goroute.smart.post.modules.article.service.PostService;
import cn.goroute.smart.rocketmq.domain.RocketMqEntityMessage;
import cn.goroute.smart.rocketmq.listener.BaseMqMessageListener;
import cn.goroute.smart.user.domain.dto.UserCollectEventDTO;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author: Alickx
 * @Date: 2023/03/12/15:50
 * @Description: 用户收藏监听器
 */
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(
	consumerGroup = MqBizConstant.UserMqConstant.USER_COLLECT_GROUP,
	topic = MqBizConstant.UserMqConstant.USER_TOPIC,
	selectorExpression = MqBizConstant.UserMqConstant.USER_COLLECT_TAG
)
public class UserCollectEventListener extends BaseMqMessageListener<RocketMqEntityMessage>
	implements RocketMQListener<RocketMqEntityMessage> {
	private final PostService postService;

	/**
	 * 消息者名称
	 *
	 * @return 消费者名称
	 */
	@Override
	protected String consumerName() {
		return "用户收藏监听器";
	}

	/**
	 * 消息处理
	 *
	 * @param message 待处理消息
	 */
	@Override
	protected void handleMessage(RocketMqEntityMessage message) {
		UserCollectEventDTO userCollectEventDTO = JSON.parseObject(message.getMessage(), UserCollectEventDTO.class);
		postService.collectPostHandle(userCollectEventDTO);
	}

	/**
	 * 超过重试次数消息，需要启用isRetry
	 *
	 * @param message 待处理消息
	 */
	@Override
	protected void overMaxRetryTimesMessage(RocketMqEntityMessage message) {

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
		return false;
	}

	@Override
	public void onMessage(RocketMqEntityMessage rocketMqEntityMessage) {
		super.dispatchMessage(rocketMqEntityMessage);
	}
}
