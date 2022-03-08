package cn.goroute.smart.thirdpart.listener;

import cn.goroute.smart.thirdpart.util.SendMailUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class MailListener {

    @Autowired
    SendMailUtil sendMailUtil;

    @RabbitListener(queues = "smart.mail")
    public void sendMail(String message) throws Exception {
        log.info("=>message={}",message);
        Map<String,Object> map = JSONObject.parseObject(message, Map.class);
        if (map != null) {
            sendMailUtil.sendTemplateMail
                    ((String) map.get("receiver"),
                            (String) map.get("subject"),
                            (String) map.get("emailTemplate"),
                            (Map<String, Object>) map.get("dataMap"));
        }
    }

}
