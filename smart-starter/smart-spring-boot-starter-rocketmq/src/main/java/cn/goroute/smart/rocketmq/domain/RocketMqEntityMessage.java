package cn.goroute.smart.rocketmq.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * RocketMQ实体类消息
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RocketMqEntityMessage extends BaseMqMessage {

	/**
	 * 业务消息
	 */
	private String message;

	/**
	 * 业务逻辑标记
	 */
	private Boolean logicFlag;
}
