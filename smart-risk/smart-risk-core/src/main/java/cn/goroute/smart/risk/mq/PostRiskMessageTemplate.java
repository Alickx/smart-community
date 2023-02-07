package cn.goroute.smart.risk.mq;

import cn.goroute.smart.common.constant.RocketMqBizConstant;
import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.rocketmq.domain.RocketMqEntityMessage;
import cn.goroute.smart.rocketmq.template.RocketMqTemplate;
import com.hccake.ballcat.common.util.JsonUtils;
import org.springframework.stereotype.Component;

/**
 * @Author: Alickx
 * @Date: 2023/01/31/12:04
 * @Description:
 */
@Component
public class PostRiskMessageTemplate extends RocketMqTemplate {

	public void sendPostMessage(Post post) {
		RocketMqEntityMessage rocketMqEntityMessage = new RocketMqEntityMessage();
		rocketMqEntityMessage.setMessage(JsonUtils.toJson(post));
		rocketMqEntityMessage.setKey(String.valueOf(post.getId()));
		rocketMqEntityMessage.setSource("文章风控事件");
		rocketMqEntityMessage.setRetryTimes(3);
		send(RocketMqBizConstant.Post.POST_TOPIC, RocketMqBizConstant.Post.POST_RISK_HANDLE_TAG, rocketMqEntityMessage);
	}

}
