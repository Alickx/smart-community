package cn.goroute.smart.user.modules.profile.mq.listener;

import cn.goroute.smart.common.constant.RocketMqBizConstant;
import cn.goroute.smart.post.domain.dto.PostPublicEventDTO;
import cn.goroute.smart.rocketmq.domain.RocketMqEntityMessage;
import cn.goroute.smart.rocketmq.listener.BaseMqMessageListener;
import cn.goroute.smart.user.modules.profile.service.UserProfileService;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
@RocketMQMessageListener(
	topic = RocketMqBizConstant.PostMqConstant.POST_TOPIC,
	consumerGroup = RocketMqBizConstant.PostMqConstant.POST_PUBLISH_EVENT_GROUP,
	consumeThreadNumber = 5
)
public class PostPublishEventListener extends BaseMqMessageListener<RocketMqEntityMessage>
	implements RocketMQListener<RocketMqEntityMessage> {


	private final UserProfileService userProfileService;

	@Override
	protected String consumerName() {
		return "文章发布事件监听器";
	}

	@Override
	protected void handleMessage(RocketMqEntityMessage message) {
		userProfileService.postPublicEventHandle(JSON.parseObject(message.getMessage(), PostPublicEventDTO.class));
	}

	@Override
	protected void overMaxRetryTimesMessage(RocketMqEntityMessage message) {
		log.error("超过最大重试次数，消息：{}", message);
	}

	@Override
	protected boolean isRetry() {
		return false;
	}

	@Override
	protected boolean isThrowException() {
		return true;
	}

	@Override
	public void onMessage(RocketMqEntityMessage rocketMqEntityMessage) {
		super.dispatchMessage(rocketMqEntityMessage);
	}
}
