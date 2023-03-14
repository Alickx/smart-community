package cn.goroute.smart.post.modules.article.mq.event;

import cn.goroute.smart.common.constant.MqBizConstant;
import cn.goroute.smart.post.domain.dto.PostPublicEventDTO;
import cn.goroute.smart.rocketmq.domain.RocketMqEntityMessage;
import cn.goroute.smart.rocketmq.template.RocketMqTemplate;
import com.alibaba.fastjson2.JSON;
import org.springframework.stereotype.Component;

@Component
public class PostPublicEventMessage extends RocketMqTemplate {

	public void sendEvent(Long postId, Long userId) {
		PostPublicEventDTO postPublicEventDTO = new PostPublicEventDTO();
		postPublicEventDTO.setPostId(postId);
		postPublicEventDTO.setUserId(userId);
		RocketMqEntityMessage rocketMqEntityMessage = new RocketMqEntityMessage();
		rocketMqEntityMessage.setMessage(JSON.toJSONString(postPublicEventDTO));
		rocketMqEntityMessage.setKey(String.valueOf(userId));
		rocketMqEntityMessage.setSource("文章发布事件");
		send(MqBizConstant.PostMqConstant.POST_PUBLISH_EVENT_GROUP, rocketMqEntityMessage);
	}

}
