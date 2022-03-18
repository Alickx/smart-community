package cn.goroute.smart.post.listener;

import cn.goroute.smart.common.dao.PostDao;
import cn.goroute.smart.common.dao.PostTagDao;
import cn.goroute.smart.common.entity.pojo.PostEntity;
import cn.goroute.smart.common.entity.pojo.PostTagEntity;
import cn.goroute.smart.common.exception.ServiceException;
import cn.goroute.smart.common.utils.IllegalTextCheckUtil;
import cn.goroute.smart.common.utils.PostConstant;
import cn.goroute.smart.post.util.PostRabbitmqUtil;
import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PostReviewListener {

    @Autowired
    IllegalTextCheckUtil textCheckUtil;

    @Autowired
    PostRabbitmqUtil postRabbitmqUtil;

    @Autowired
    PostTagDao postTagDao;

    @Autowired
    PostDao postDao;

    @RabbitListener(queues = "smart.post.review")
    public void review(PostEntity postEntity, List<Integer> tageUidList) {
        log.info("开始审核文章内容");

        List<String> titleCheckResult = textCheckUtil.checkText(postEntity.getTitle());
        List<String> contentCheckResult = textCheckUtil.checkText(postEntity.getContent());

        //审核文章
        if (titleCheckResult.size() != 0 || contentCheckResult.size() != 0) {
            postEntity.setStatus(PostConstant.INVISIBLE_STATUS);
            log.info("文章内容含有违禁词，违禁词为=>{}", CollectionUtil.addAll(titleCheckResult, contentCheckResult).stream().distinct().collect(Collectors.toList()));
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
            postRabbitmqUtil.saveEs(postEntity);
        }
        tageUidList.forEach(t -> {
            PostTagEntity postTagEntity = new PostTagEntity();
            postTagEntity.setPostUid(postEntity.getUid());
            postTagEntity.setTagUid(t);
            postTagDao.insert(postTagEntity);
        });

    }

}
