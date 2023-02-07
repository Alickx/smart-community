package cn.goroute.smart.post.mq;

import cn.goroute.smart.common.constant.RocketMqBizConstant;
import cn.goroute.smart.rocketmq.domain.RocketMqEntityMessage;
import cn.goroute.smart.rocketmq.template.RocketMqTemplate;
import cn.goroute.smart.search.model.index.PostIndex;
import com.hccake.ballcat.common.util.JsonUtils;
import org.springframework.stereotype.Component;

/**
 * @Author: Alickx
 * @Date: 2023/01/31/12:04
 * @Description:
 */
@Component
public class PostSyncMessageTemplate extends RocketMqTemplate {

	public void sendPostMessage(PostIndex postIndex) {
		RocketMqEntityMessage rocketMqEntityMessage = new RocketMqEntityMessage();
		rocketMqEntityMessage.setMessage(JsonUtils.toJson(postIndex));
		rocketMqEntityMessage.setKey(String.valueOf(postIndex.getId()));
		rocketMqEntityMessage.setSource("文章风控事件");
		rocketMqEntityMessage.setRetryTimes(3);
		send(RocketMqBizConstant.Post.POST_TOPIC, RocketMqBizConstant.Post.POST_SYNC_SAVE_ES_HANDLE_TAG, rocketMqEntityMessage);
	}

}
