package cn.goroute.smart.post.listener;

import cn.goroute.smart.common.dao.PostDao;
import cn.goroute.smart.common.dao.PostTagDao;
import cn.goroute.smart.common.entity.pojo.Post;
import cn.goroute.smart.common.entity.pojo.PostTag;
import cn.goroute.smart.common.exception.ServiceException;
import cn.goroute.smart.common.utils.IllegalTextCheckUtil;
import cn.goroute.smart.common.utils.PostConstant;
import cn.goroute.smart.post.util.RabbitmqUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class PostReviewListener {

    @Autowired
    IllegalTextCheckUtil textCheckUtil;

    @Autowired
    RabbitmqUtil rabbitmqUtil;

    @Autowired
    PostTagDao postTagDao;

    @Autowired
    PostDao postDao;

    @RabbitListener(queues = "smart.post.review")
    public void review(Map map) {
        log.info("开始审核文章内容");
        Post postEntity = JSONUtil.toBean((String) map.get("post"), Post.class);
        List<Integer> tagUidList = JSONUtil.toList((String) map.get("tagUidList"), Integer.class);
        Boolean titleCheckResult = textCheckUtil.checkText(postEntity.getTitle());
        Boolean contentCheckResult = textCheckUtil.checkText(postEntity.getContent());

        //审核文章
        if (titleCheckResult || contentCheckResult) {
            postEntity.setStatus(PostConstant.INVISIBLE_STATUS);
            log.info("文章内容含有违禁词");
            int result = postDao.updateById(postEntity);
            if (result != 1) {
                throw new ServiceException("文章存入数据库失败");
            }
            return;
        } else {
            log.info("文章初步审核无违禁词");
            postEntity.setStatus(PostConstant.NORMAL_STATUS);
            int result = postDao.updateById(postEntity);
            if (result != 1) {
                throw new ServiceException("文章存入数据库失败");
            }
        }
        //审核通过则更新文章
        if (Objects.equals(postEntity.getIsPublish(), "1")) {
            log.info("文章正常，开始存入es");
            rabbitmqUtil.saveEs(postEntity);
        }
        tagUidList.forEach(t -> {
            PostTag postTag = new PostTag();
            postTag.setPostUid(postEntity.getUid());
            postTag.setTagUid(t);
            postTagDao.insert(postTag);
        });

    }

}
