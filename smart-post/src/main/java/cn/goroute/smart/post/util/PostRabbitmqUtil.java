package cn.goroute.smart.post.util;

import cn.goroute.smart.common.entity.pojo.PostEntity;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PostRabbitmqUtil {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void saveEs(PostEntity postEntity){

        log.info("发送消息队列，更新es数据,postId=>{}",postEntity.getUid());
        rabbitTemplate
                .convertAndSend("exchange.direct"
                        ,"smart.search.post"
                        , JSONUtil.toJsonStr(postEntity));
        log.info("文章保存ES消息发送完毕");

    }

    public void transPostCount2ES(PostEntity postEntity) {
        log.info("发送消息队列，更新文章点赞评论数量,post=>{}",postEntity);
        rabbitTemplate
                .convertAndSend("exchange.direct",
                        "smart.search.post.durability",
                        JSONUtil.toJsonStr(postEntity));
        log.info("更新文章点赞评论数量消息发送完毕");
    }

}
