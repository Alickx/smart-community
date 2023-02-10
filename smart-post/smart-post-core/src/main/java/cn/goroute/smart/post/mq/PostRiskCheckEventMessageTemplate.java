package cn.goroute.smart.post.mq;

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
public class PostRiskCheckEventMessageTemplate extends RocketMqTemplate {

	public void sendPostMessage(Post post) {
		RocketMqEntityMessage rocketMqEntityMessage = new RocketMqEntityMessage();
		rocketMqEntityMessage.setMessage(JsonUtils.toJson(post));
		rocketMqEntityMessage.setKey(String.valueOf(post.getId()));
		rocketMqEntityMessage.setSource("文章风控检查事件");
		rocketMqEntityMessage.setRetryTimes(3);
		sendAsync(RocketMqBizConstant.Post.POST_TOPIC, RocketMqBizConstant.Post.POST_RISK_HANDLE_TAG, rocketMqEntityMessage);
	}

}
