package cn.goroute.smart.user.modules.follow.mq;

import cn.goroute.smart.common.constant.MqBizConstant;
import cn.goroute.smart.rocketmq.domain.RocketMqEntityMessage;
import cn.goroute.smart.rocketmq.template.RocketMqTemplate;
import cn.goroute.smart.user.domain.dto.UserFollowEventDTO;
import cn.goroute.smart.user.domain.entity.UserFollowEntity;
import cn.goroute.smart.user.modules.follow.converter.UserFollowConverter;
import com.alibaba.fastjson2.JSON;
import org.springframework.stereotype.Component;

/**
 * @Author: Alickx
 * @Date: 2023/03/11/9:57
 * @Description:
 */
@Component
public class UserFollowEventTemplate extends RocketMqTemplate {

	public void sendUserFollowEvent(UserFollowEntity userFollowEntity) {

		UserFollowEventDTO userFollowEventDTO = UserFollowConverter.INSTANCE.poToDto(userFollowEntity);
		RocketMqEntityMessage rocketMqEntityMessage = new RocketMqEntityMessage();
		rocketMqEntityMessage.setSource("用户关注事件");
		rocketMqEntityMessage.setMessage(JSON.toJSONString(userFollowEventDTO));
		rocketMqEntityMessage.setKey(userFollowEntity.getId().toString());
		rocketMqEntityMessage.setRetryTimes(0);

		this.send(MqBizConstant.UserMqConstant.USER_TOPIC, MqBizConstant.UserMqConstant.USER_FOLLOW_TAG, rocketMqEntityMessage);
	}

}
