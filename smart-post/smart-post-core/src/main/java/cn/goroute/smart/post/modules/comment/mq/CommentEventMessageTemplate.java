package cn.goroute.smart.post.modules.comment.mq;

import cn.goroute.smart.common.constant.MqBizConstant;
import cn.goroute.smart.post.domain.dto.CommentDTO;
import cn.goroute.smart.rocketmq.domain.RocketMqEntityMessage;
import cn.goroute.smart.rocketmq.template.RocketMqTemplate;
import com.alibaba.fastjson2.JSON;
import org.springframework.stereotype.Component;

/**
 * @Author: Alickx
 * @Date: 2023/01/07/11:49
 * @Description:
 */
@Component
public class CommentEventMessageTemplate extends RocketMqTemplate {


    public void sendPostCommentMessage(CommentDTO commentDTO) {
        RocketMqEntityMessage rocketMqEntityMessage = new RocketMqEntityMessage();
        rocketMqEntityMessage.setMessage(JSON.toJSONString(commentDTO));
        rocketMqEntityMessage.setKey(commentDTO.getId().toString());
        rocketMqEntityMessage.setSource("评论信息");
        rocketMqEntityMessage.setRetryTimes(3);

        sendAsync(MqBizConstant.CommentMqConstant.COMMENT_TOPIC, rocketMqEntityMessage);
    }

}
