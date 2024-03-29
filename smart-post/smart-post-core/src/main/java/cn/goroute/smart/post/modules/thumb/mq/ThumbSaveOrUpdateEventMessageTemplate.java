package cn.goroute.smart.post.modules.thumb.mq;

import cn.goroute.smart.common.constant.MqBizConstant;
import cn.goroute.smart.post.domain.dto.ThumbDTO;
import cn.goroute.smart.rocketmq.domain.RocketMqEntityMessage;
import cn.goroute.smart.rocketmq.template.RocketMqTemplate;
import com.alibaba.fastjson2.JSON;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/10/22/16:30
 * @Description: 点赞消息模板
 */
@Component
public class ThumbSaveOrUpdateEventMessageTemplate extends RocketMqTemplate{

	/**
	 * 文章点赞
	 */
	public void sendPostThumbMessage(ThumbDTO thumbDTO,Boolean saveFlag) {
		RocketMqEntityMessage message = getRocketMqEntityMessage(thumbDTO,saveFlag);
		sendAsync(MqBizConstant.ThumbMqConstant.THUMB_TOPIC, message);
	}


	@NotNull
	private static RocketMqEntityMessage getRocketMqEntityMessage(ThumbDTO thumbDTO,Boolean saveFlag) {
		RocketMqEntityMessage message = new RocketMqEntityMessage();
		thumbDTO.setSaveFlag(saveFlag);
		message.setRetryTimes(3);
		message.setSource("点赞信息");
		message.setMessage(JSON.toJSONString(thumbDTO));
		return message;
	}

}
