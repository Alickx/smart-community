package cn.goroute.smart.user.modules.collect.mq.event;

import cn.goroute.smart.common.constant.MqBizConstant;
import cn.goroute.smart.rocketmq.domain.RocketMqEntityMessage;
import cn.goroute.smart.rocketmq.template.RocketMqTemplate;
import cn.goroute.smart.user.domain.dto.UserCollectEventDTO;
import com.alibaba.fastjson2.JSON;
import org.springframework.stereotype.Component;

/**
 * @Author: Alickx
 * @Date: 2023/03/12/16:29
 * @Description:
 */
@Component
public class UserCollectPostEventTemplate extends RocketMqTemplate {


	public void sendEvent(Long postId, Long userId, Integer collectStatus) {
		UserCollectEventDTO userCollectEventDTO = new UserCollectEventDTO();
		userCollectEventDTO.setPostId(postId);
		userCollectEventDTO.setUserId(userId);
		userCollectEventDTO.setCollectStatus(collectStatus);
		RocketMqEntityMessage rocketMqEntityMessage = new RocketMqEntityMessage();
		rocketMqEntityMessage.setMessage(JSON.toJSONString(userCollectEventDTO));
		rocketMqEntityMessage.setKey(String.valueOf(userId));
		rocketMqEntityMessage.setSource("文章收藏事件");
		send(MqBizConstant.UserMqConstant.USER_TOPIC,MqBizConstant.UserMqConstant.USER_COLLECT_TAG,rocketMqEntityMessage);
	}

}
