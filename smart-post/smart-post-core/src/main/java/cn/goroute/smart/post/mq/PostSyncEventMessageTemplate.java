package cn.goroute.smart.post.mq;

import cn.goroute.smart.common.constant.RocketMqBizConstant;
import cn.goroute.smart.post.domain.entity.PostEntity;
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
public class PostSyncEventMessageTemplate extends RocketMqTemplate {

	public void sendPostMessage(PostEntity postEntity) {
		RocketMqEntityMessage rocketMqEntityMessage = new RocketMqEntityMessage();
		rocketMqEntityMessage.setMessage(JsonUtils.toJson(postEntity));
		rocketMqEntityMessage.setKey(String.valueOf(postEntity.getId()));
		rocketMqEntityMessage.setSource("文章风控事件");
		rocketMqEntityMessage.setRetryTimes(3);
		send(RocketMqBizConstant.PostMqConstant.POST_TOPIC, RocketMqBizConstant.PostMqConstant.POST_SYNC_SAVE_ES_HANDLE_TAG, rocketMqEntityMessage);
	}

}
