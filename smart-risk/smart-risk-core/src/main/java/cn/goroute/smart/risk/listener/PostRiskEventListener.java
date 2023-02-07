package cn.goroute.smart.risk.listener;

import cn.goroute.smart.common.constant.RocketMqBizConstant;
import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.risk.service.PostRiskService;
import cn.goroute.smart.rocketmq.domain.RocketMqEntityMessage;
import cn.goroute.smart.rocketmq.listener.BaseMqMessageListener;
import com.hccake.ballcat.common.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author: Alickx
 * @Date: 2023/02/04 22:39:47
 * @Description: 文章发布事件监听器
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(
		topic = RocketMqBizConstant.Post.POST_TOPIC,
		consumerGroup = RocketMqBizConstant.Post.POST_RISK_HANDLE_GROUP,
		selectorExpression = RocketMqBizConstant.Post.POST_RISK_HANDLE_TAG,
		consumeThreadNumber = 5
)
public class PostRiskEventListener extends BaseMqMessageListener<RocketMqEntityMessage>
		implements RocketMQListener<RocketMqEntityMessage> {

	private final PostRiskService postRiskService;

	@Override
	protected String consumerName() {
		return "文章发布风控监听者";
	}

	@Override
	protected void handleMessage(RocketMqEntityMessage message) throws Exception {
		Post post = JsonUtils.toObj(message.getMessage(), Post.class);
		postRiskService.checkPostRisk(post);
	}

	@Override
	protected void overMaxRetryTimesMessage(RocketMqEntityMessage message) {
		log.error("文章发布事件监听者重试超过最大重试次数，消息内容：[{}]", JsonUtils.toJson(message));
	}

	@Override
	protected boolean isRetry() {
		return true;
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
