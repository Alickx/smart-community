package cn.goroute.smart.search.listener;

import cn.goroute.smart.common.entity.pojo.Post;
import cn.goroute.smart.search.model.PostEsModel;
import cn.goroute.smart.search.service.PostSearchService;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class PostListener {

    @Autowired
    PostSearchService service;

    @RabbitListener(queues = "smart.search.post")
    public void savePost(String message) throws IOException {
        log.info("=>接收到消息,开始存入es中,message=>{}",message);
        PostEsModel postEsModel = new PostEsModel();
        Post post = JSONUtil.toBean(message, Post.class);
        BeanUtils.copyProperties(post, postEsModel);
        String resp = service.save(postEsModel);
        log.info("=>保存结束，resp=>{}",resp);
    }

    @RabbitListener(queues = "smart.search.post.durability")
    public void transPost2ESListener(String message) throws IOException {
        log.info("接收到消息，开始同步文章信息=>{}",message);
        Post post = JSONUtil.toBean(message, Post.class);
        service.transPost2ES(post);
    }



}
