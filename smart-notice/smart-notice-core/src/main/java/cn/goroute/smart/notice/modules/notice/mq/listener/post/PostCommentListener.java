package cn.goroute.smart.notice.modules.notice.mq.listener.post;

import cn.goroute.smart.common.constant.MqBizConstant;
import cn.goroute.smart.notice.modules.notice.service.NoticeService;
import cn.goroute.smart.post.domain.dto.CommentDTO;
import cn.goroute.smart.post.domain.entity.CommentEntity;
import cn.goroute.smart.rocketmq.domain.RocketMqEntityMessage;
import cn.goroute.smart.rocketmq.listener.BaseMqMessageListener;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author: Alickx
 * @Date: 2023/01/07/10:56
 * @Description: 评论监听器
 */
@RequiredArgsConstructor
@Component
@Slf4j
@RocketMQMessageListener(
		topic = MqBizConstant.CommentMqConstant.COMMENT_TOPIC,
		consumerGroup = MqBizConstant.CommentMqConstant.COMMENT_NOTICE_GROUP,
		consumeThreadNumber = 5
)
public class PostCommentListener extends BaseMqMessageListener<RocketMqEntityMessage>
		implements RocketMQListener<RocketMqEntityMessage> {


	private final NoticeService noticeService;

	/**
	 * 消息者名称
	 *
	 * @return 消费者名称
	 */
	@Override
	protected String consumerName() {
		return "文章评论监听器";
	}

	/**
	 * 消息处理
	 *
	 * @param message 待处理消息
	 */
	@Override
	protected void handleMessage(RocketMqEntityMessage message) {
		CommentDTO commentDTO = JSON.parseObject(message.getMessage(), CommentDTO.class);
		noticeService.saveCommentNotice(commentDTO);
	}

	/**
	 * 超过重试次数消息，需要启用isRetry
	 *
	 * @param message 待处理消息
	 */
	@Override
	protected void overMaxRetryTimesMessage(RocketMqEntityMessage message) {
		log.error("文章评论监听超过重试次数,消息内容:[{}]", JSON.parseObject(message.getMessage(), CommentEntity.class).toString());
	}

	/**
	 * 是否异常时重复发送
	 *
	 * @return true: 消息重试，false：不重试
	 */
	@Override
	protected boolean isRetry() {
		return false;
	}

	/**
	 * 消费异常时是否抛出异常
	 *
	 * @return true: 抛出异常，false：消费异常(如果没有开启重试则消息会被自动ack)
	 */
	@Override
	protected boolean isThrowException() {
		return true;
	}

	@Override
	public void onMessage(RocketMqEntityMessage rocketMqEntityMessage) {
		super.dispatchMessage(rocketMqEntityMessage);
	}
}
