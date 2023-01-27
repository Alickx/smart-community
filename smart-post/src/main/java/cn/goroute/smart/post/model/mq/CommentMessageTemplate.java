package cn.goroute.smart.post.model.mq;

import cn.goroute.smart.common.util.JsonUtil;
import cn.goroute.smart.post.constant.RocketMqBizConstant;
import cn.goroute.smart.post.domain.Comment;
import cn.goroute.smart.rocketmq.domain.RocketMqEntityMessage;
import cn.goroute.smart.rocketmq.template.RocketMqTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.stereotype.Component;

/**
 * @Author: Alickx
 * @Date: 2023/01/07/11:49
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class CommentMessageTemplate extends RocketMqTemplate {


    public SendResult sendPostCommentMessage(Comment comment) {
        RocketMqEntityMessage rocketMqEntityMessage = new RocketMqEntityMessage();
        rocketMqEntityMessage.setMessage(JsonUtil.toJsonString(comment));
        rocketMqEntityMessage.setKey(comment.getId().toString());
        rocketMqEntityMessage.setSource("评论信息");
        rocketMqEntityMessage.setRetryTimes(3);

        return send(RocketMqBizConstant.Comment.COMMENT_TOPIC,
                RocketMqBizConstant.Comment.COMMENT_HANDLE_TAG, rocketMqEntityMessage);
    }

}
