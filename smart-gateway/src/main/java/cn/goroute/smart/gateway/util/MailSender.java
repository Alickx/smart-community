package cn.goroute.smart.gateway.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class MailSender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendCaptcha(String receiver, String subject, String emailTemplate, Map<String,Object> dataMap){
        log.info("=>开始发送消息,邮件接收者{},主题:{},邮件模板:{},邮件变量:{}",receiver,subject,emailTemplate,dataMap);
        Map<String,Object> map = new HashMap<>();

        map.put("receiver",receiver);
        map.put("subject",subject);
        map.put("emailTemplate",emailTemplate);
        map.put("dataMap",dataMap);

        rabbitTemplate.convertAndSend("exchange.direct","smart.mail", JSONObject.toJSONString(map));

    }

}
