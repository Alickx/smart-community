package cn.goroute.smart.post.model.mq;

import cn.goroute.smart.common.util.JsonUtil;
import cn.goroute.smart.post.constant.RocketMqBizConstant;
import cn.goroute.smart.post.model.dto.ThumbDTO;
import cn.goroute.smart.rocketmq.domain.RocketMqEntityMessage;
import cn.goroute.smart.rocketmq.template.RocketMqTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.rocketmq.client.producer.SendResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/10/22/16:30
 * @Description: 点赞消息模板
 */
@EqualsAndHashCode(callSuper = true)
@Component
@Data
public class ThumbMessageTemplate extends RocketMqTemplate{

	/**
	 * 构建文章点赞消息模板
	 * @param userId 用户id
	 * @param toId 被点赞对象id
	 * @param type 点赞类型
	 * @return 点赞消息模板
	 */
	public SendResult sendPostThumbMessage(Long userId, Long toId, Integer type) {
		RocketMqEntityMessage message = getRocketMqEntityMessage(userId, toId, type,true);
		return send(RocketMqBizConstant.Thumb.THUMB_TOPIC, RocketMqBizConstant.Thumb.THUMB_HANDLE_TAG, message);
	}

	/**
	 * 构建文章点赞消息模板
	 * @param userId 用户id
	 * @param toId 被点赞对象id
	 * @param type 点赞类型
	 * @return 点赞消息模板
	 */
	public SendResult sendPostCancelThumb(Long userId, Long toId, Integer type) {
		RocketMqEntityMessage message = getRocketMqEntityMessage(userId, toId, type,false);
		return send(RocketMqBizConstant.Thumb.THUMB_TOPIC, RocketMqBizConstant.Thumb.THUMB_HANDLE_TAG, message);
	}

	@NotNull
	private static RocketMqEntityMessage getRocketMqEntityMessage(Long userId, Long toId, Integer type,Boolean logicFlag) {
		RocketMqEntityMessage message = new RocketMqEntityMessage();
		ThumbDTO thumbDTO = new ThumbDTO();
		thumbDTO.setUserId(userId);
		thumbDTO.setToId(toId);
		thumbDTO.setType(type);
		thumbDTO.setIsSave(logicFlag);
		message.setRetryTimes(3);
		message.setSource("点赞信息");
		message.setMessage(JsonUtil.toJsonString(thumbDTO));
		return message;
	}

}