package cn.goroute.smart.post.util;

import cn.goroute.smart.post.mapper.PostMapper;
import cn.goroute.smart.common.entity.pojo.EventRemind;
import cn.goroute.smart.post.entity.pojo.Post;
import cn.goroute.smart.common.exception.ServiceException;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alickx
 */
@Service
@Slf4j
public class RabbitmqUtil {

    private static final String EXCHANGE = "exchange.direct";

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    PostMapper postMapper;

    /**
     * 保存文章到es
     *
     * @param post 文章实体类
     */
    public void saveEs(Post post) {

        log.info("发送消息队列，更新es数据,postId=>{}", post.getUid());
        rabbitTemplate
                .convertAndSend(EXCHANGE
                        , "smart.search.post"
                        , JSONUtil.toJsonStr(post));
        log.info("文章保存ES消息发送完毕");

    }

    /**
     * 持久化文章点赞，评论数到es
     *
     * @param post 文章实体类
     */
    public void transPost2ESUtil(Post post) {
        log.info("发送消息队列，更新文章点赞评论数量,post=>{}", post);
        rabbitTemplate
                .convertAndSend(EXCHANGE,
                        "smart.search.post.durability",
                        JSONUtil.toJsonStr(post));
        log.info("更新文章点赞评论数量消息发送完毕");
    }

    /**
     * 审核文章内容
     *
     * @param post       文章实体类
     * @param tagUidList 文章标签集合
     */
    public void reviewPost(Post post, List<Long> tagUidList,Boolean isUpdate) {
        log.info("发送消息队列，审查文章内容");
        Map<String, Object> map = new HashMap<>(3);
        map.put("post", JSONUtil.toJsonStr(post));
        map.put("tagUidList", JSONUtil.toJsonStr(tagUidList));
        map.put("isUpdate",isUpdate);
        rabbitTemplate.convertAndSend(EXCHANGE,
                "smart.post.review", map);
        log.info("文章审核消息发送完毕");
    }

    /**
     * 发送消息队列，发送普通通知
     *
     * @param eventRemind 事件提醒实体类
     */
    public void sendEventRemind(EventRemind eventRemind) {
        if (eventRemind == null) {
            throw new ServiceException("发送普通通知失败，参数为空");
        }
        log.info("发送消息队列，发送事件提醒消息,eventRemind=>{}", eventRemind);
        rabbitTemplate.convertAndSend(EXCHANGE, "smart.event.remind", eventRemind);
    }

}
