package cn.goroute.smart.notice.modules.notice.mq.listener.user;

import cn.goroute.smart.common.constant.MqBizConstant;
import cn.goroute.smart.notice.modules.notice.service.NoticeService;
import cn.goroute.smart.rocketmq.domain.RocketMqEntityMessage;
import cn.goroute.smart.rocketmq.listener.BaseMqMessageListener;
import cn.goroute.smart.user.domain.dto.UserFollowEventDTO;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author: Alickx
 * @Date: 2023/03/11/10:20
 * @Description: 用户关注事件监听者
 */
@RequiredArgsConstructor
@Component
@Slf4j
@RocketMQMessageListener(
	topic = MqBizConstant.UserMqConstant.USER_TOPIC,
	consumerGroup = MqBizConstant.UserMqConstant.USER_FOLLOW_GROUP,
	selectorExpression = MqBizConstant.UserMqConstant.USER_FOLLOW_TAG,
	consumeThreadNumber = 5
)
public class UserFollowEventListener extends BaseMqMessageListener<RocketMqEntityMessage>
	implements RocketMQListener<RocketMqEntityMessage> {

	private final NoticeService noticeService;

	/**
	 * 消息者名称
	 *
	 * @return 消费者名称
	 */
	@Override
	protected String consumerName() {
		return "用户关注消费者";
	}

	/**
	 * 消息处理
	 *
	 * @param message 待处理消息
	 * @throws Exception 消费异常
	 */
	@Override
	protected void handleMessage(RocketMqEntityMessage message) throws Exception {
		noticeService.saveUserFollowNotice(JSON.parseObject(message.getMessage(), UserFollowEventDTO.class));
	}

	/**
	 * 超过重试次数消息，需要启用isRetry
	 *
	 * @param message 待处理消息
	 */
	@Override
	protected void overMaxRetryTimesMessage(RocketMqEntityMessage message) {
		// 入库
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
