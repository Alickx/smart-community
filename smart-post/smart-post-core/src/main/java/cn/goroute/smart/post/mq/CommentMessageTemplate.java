package cn.goroute.smart.post.mq;

import cn.goroute.smart.common.constant.RocketMqBizConstant;
import cn.goroute.smart.post.domain.Comment;
import cn.goroute.smart.rocketmq.domain.RocketMqEntityMessage;
import cn.goroute.smart.rocketmq.template.RocketMqTemplate;
import com.hccake.ballcat.common.util.JsonUtils;
import org.springframework.stereotype.Component;

/**
 * @Author: Alickx
 * @Date: 2023/01/07/11:49
 * @Description:
 */
@Component
public class CommentMessageTemplate extends RocketMqTemplate {


    public void sendPostCommentMessage(Comment comment) {
        RocketMqEntityMessage rocketMqEntityMessage = new RocketMqEntityMessage();
        rocketMqEntityMessage.setMessage(JsonUtils.toJson(comment));
        rocketMqEntityMessage.setKey(comment.getId().toString());
        rocketMqEntityMessage.setSource("评论信息");
        rocketMqEntityMessage.setRetryTimes(3);

        sendAsync(RocketMqBizConstant.Comment.COMMENT_TOPIC,
                RocketMqBizConstant.Comment.COMMENT_HANDLE_TAG, rocketMqEntityMessage);
    }

}
