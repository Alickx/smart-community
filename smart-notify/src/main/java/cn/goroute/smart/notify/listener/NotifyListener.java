package cn.goroute.smart.notify.listener;

import cn.goroute.smart.common.entity.pojo.EventRemind;
import cn.goroute.smart.notify.service.EventRemindService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: Alickx
 * @Date: 2022/03/22/20:30
 * @Description: 论坛消息监听处理类
 */
@Component
@Slf4j
@RabbitListener(queues = "smart.event.remind.queue")
public class NotifyListener {

    @Autowired
    EventRemindService eventRemindService;

    @RabbitHandler
    public void saveEventRemind(EventRemind eventRemind, Channel channel, Message msg) throws IOException {
        try {
            log.info("接收到消息：{}", eventRemind);
            if (eventRemind != null){
                eventRemindService.save(eventRemind);
                channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
            }
        } catch (Exception e) {
            if (msg.getMessageProperties().getRedelivered()) {
                log.error("消息已重复处理,拒绝消息,内容:{}", eventRemind, e);
                channel.basicReject(msg.getMessageProperties().getDeliveryTag(), false);
            } else {
                log.error("拒绝消息,内容:{}", eventRemind, e);
                channel.basicNack(msg.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }


}
