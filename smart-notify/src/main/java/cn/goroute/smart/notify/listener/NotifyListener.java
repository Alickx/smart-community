package cn.goroute.smart.notify.listener;

import cn.goroute.smart.common.entity.pojo.EventRemind;
import cn.goroute.smart.notify.service.EventRemindService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Alickx
 * @Date: 2022/03/22/20:30
 * @Description: 论坛消息监听处理类
 */
@Component
@Slf4j
public class NotifyListener {

    @Autowired
    EventRemindService eventRemindService;

    @RabbitListener(queues = "smart.event.remind.queue")
    public void saveEventRemind(EventRemind eventRemind) {
        log.info("接收到消息：{}", eventRemind);
        if (eventRemind != null){
            boolean save = eventRemindService.save(eventRemind);
            if (save){
                log.info("事件提醒保存成功");
            } else {
                log.warn("事件提醒保存失败");
            }
        }
        log.info("消息处理完成");
    }


}
