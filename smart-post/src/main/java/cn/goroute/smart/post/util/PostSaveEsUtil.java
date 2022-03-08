package cn.goroute.smart.post.util;

import cn.goroute.smart.common.entity.PostEntity;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PostSaveEsUtil {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void saveEs(PostEntity postEntity){

        log.info("发送消息队列，更新es数据,postId=>{}",postEntity.getUid());
        rabbitTemplate
                .convertAndSend("exchange.direct"
                        ,"smart.search.post"
                        , JSONObject.toJSONString(postEntity));
        log.info("=>发送完毕");

    }

}
