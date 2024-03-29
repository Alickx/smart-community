package cn.goroute.smart.rocketmq.template;

import cn.goroute.smart.rocketmq.constant.RocketMqSysConstant;
import cn.goroute.smart.rocketmq.domain.BaseMqMessage;
import cn.goroute.smart.rocketmq.util.JsonUtil;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * RocketMQ模板类
 */
@Component
public class RocketMqTemplate {
	private static final Logger LOGGER = LoggerFactory.getLogger(RocketMqTemplate.class);

	@Resource(name = "rocketMQTemplate")
	private RocketMQTemplate template;

	/**
	 * 获取模板，如果封装的方法不够提供原生的使用方式
	 */
	public RocketMQTemplate getTemplate() {
		return template;
	}

	/**
	 * 构建目的地
	 */
	public String buildDestination(String topic, String tag) {
		return topic + RocketMqSysConstant.DELIMITER + tag;
	}

	/**
	 * 发送同步消息
	 */
	public <T extends BaseMqMessage> SendResult send(String topic, String tag, T message) {
		// 注意分隔符
		return send(topic + RocketMqSysConstant.DELIMITER + tag, message);
	}

	public <T extends BaseMqMessage> SendResult send(String destination, T message) {
		// 设置业务键，此处根据公共的参数进行处理
		Message<T> sendMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, message.getKey()).build();
		SendResult sendResult = template.syncSend(destination, sendMessage);
		// 此处为了方便查看给日志转了json，根据选择选择日志记录方式，例如ELK采集
		LOGGER.info("[{}]同步消息[{}]发送结果[{}]", destination, JsonUtil.toJson(message), JsonUtil.toJson(sendResult));
		return sendResult;
	}

	/**
	 * 发送异步消息
	 */
	public <T extends BaseMqMessage> void sendAsync(String topic, String tag, T message) {
		sendAsync(topic + RocketMqSysConstant.DELIMITER + tag, message);
	}

	public <T extends BaseMqMessage> void sendAsync(String destination, T message) {
		Message<T> sendMessage = MessageBuilder
				.withPayload(message)
				.setHeader(RocketMQHeaders.KEYS, message.getKey()).build();
		template.asyncSend(destination, sendMessage, new SendCallback() {
			@Override
			public void onSuccess(SendResult sendResult) {
				LOGGER.info("[{}]异步消息[{}]发送结果[{}]", destination, JsonUtil.toJson(message), JsonUtil.toJson(sendResult));
			}

			@Override
			public void onException(Throwable e) {
				LOGGER.error("[{}]异步消息[{}]发送异常[{}]", destination, JsonUtil.toJson(message), e.getMessage());
			}
		});
	}

	/**
	 * 发送延迟消息
	 */
	public <T extends BaseMqMessage> SendResult send(String topic, String tag, T message, int delayLevel) {
		return send(topic + RocketMqSysConstant.DELIMITER + tag, message, delayLevel);
	}

	public <T extends BaseMqMessage> SendResult send(String destination, T message, int delayLevel) {
		Message<T> sendMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, message.getKey()).build();
		SendResult sendResult = template.syncSend(destination, sendMessage, 3000, delayLevel);
		LOGGER.info("[{}]延迟等级[{}]消息[{}]发送结果[{}]", destination, delayLevel, JsonUtil.toJson(message), JsonUtil.toJson(sendResult));
		return sendResult;
	}
}
