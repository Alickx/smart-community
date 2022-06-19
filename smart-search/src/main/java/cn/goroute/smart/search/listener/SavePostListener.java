package cn.goroute.smart.search.listener;

import cn.goroute.smart.common.entity.pojo.Post;
import cn.goroute.smart.search.model.PostEsModel;
import cn.goroute.smart.search.service.PostSearchService;
import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: Alickx
 * @Date: 2022/06/19/15:40
 * @Description: 文件存入ES 消息队列监听器
 */
@RabbitListener(queues = "smart.search.post")
@Slf4j
@Component
public class SavePostListener {

    @Autowired
    PostSearchService service;

    @RabbitHandler
    public void savePost(String message, Channel channel, Message msg) throws IOException {
        try {
            PostEsModel postEsModel = new PostEsModel();
            Post post = JSONUtil.toBean(message, Post.class);
            BeanUtils.copyProperties(post, postEsModel);
            String resp = service.save(postEsModel);
            log.info("=>保存结束，resp=>{}", resp);
            channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            if (msg.getMessageProperties().getRedelivered()) {
                log.error("文章保存失败,消息已重复处理,拒绝消息,内容:{}", message, e);
                channel.basicReject(msg.getMessageProperties().getDeliveryTag(), false);
                //TODO 存入数据库作人工补偿
            } else {
                log.error("文章保存失败,拒绝消息,内容:{}", message, e);
                channel.basicNack(msg.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }
}
