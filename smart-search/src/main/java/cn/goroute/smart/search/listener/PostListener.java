package cn.goroute.smart.search.listener;

import cn.goroute.smart.common.entity.PostEntity;
import cn.goroute.smart.search.model.PostEsModel;
import cn.goroute.smart.search.service.PostSearchService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PostListener {

    @Autowired
    PostSearchService service;

    @RabbitListener(queues = "smart.search.post")
    public void savePost(String message) throws Exception {
        log.info("=>接收到消息,开始存入es中");

        PostEsModel postEsModel = new PostEsModel();

        PostEntity post = JSONObject.parseObject(message,PostEntity.class);
        BeanUtils.copyProperties(post, postEsModel);

        String resp = service.save(postEsModel);
        log.info("=>保存结束，resp=>{}",resp);

    }



}
