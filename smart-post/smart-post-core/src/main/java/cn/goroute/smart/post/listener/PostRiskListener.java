package cn.goroute.smart.post.listener;

import cn.goroute.smart.common.constant.RocketMqBizConstant;
import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.post.service.PostService;
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
 * @Date: 2023/02/06 20:42:14
 * @Description: 文章风控监听器
 */
@RequiredArgsConstructor
@Component
@Slf4j
@RocketMQMessageListener(
		topic = RocketMqBizConstant.Post.POST_TOPIC,
		consumerGroup = RocketMqBizConstant.Post.POST_RISK_HANDLE_GROUP,
		selectorExpression = RocketMqBizConstant.Post.POST_RISK_HANDLE_TAG,
		consumeThreadNumber = 5
)
public class PostRiskListener extends BaseMqMessageListener<RocketMqEntityMessage>
		implements RocketMQListener<RocketMqEntityMessage> {


	private final PostService postService;

	@Override
	protected String consumerName() {
		return "文章风控监听器";
	}

	@Override
	protected void handleMessage(RocketMqEntityMessage message) throws Exception {
		Post post = JsonUtils.toObj(message.getMessage(), Post.class);
		postService.PostRiskHandler(post);
	}

	@Override
	protected void overMaxRetryTimesMessage(RocketMqEntityMessage message) {
		log.info("文章风控监听器超过重试次数,消息内容:[{}]", message.getMessage());
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
