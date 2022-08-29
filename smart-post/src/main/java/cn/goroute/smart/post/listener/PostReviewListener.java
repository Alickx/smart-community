package cn.goroute.smart.post.listener;

import cn.goroute.smart.common.constant.PostConstant;
import cn.goroute.smart.common.exception.ServiceException;
import cn.goroute.smart.post.entity.pojo.Post;
import cn.goroute.smart.post.entity.pojo.PostTag;
import cn.goroute.smart.post.mapper.PostMapper;
import cn.goroute.smart.post.mapper.PostTagMapper;
import cn.goroute.smart.post.util.RabbitmqUtil;
import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
@RabbitListener(queues = "smart.post.review")
public class PostReviewListener {

    @Autowired
    RabbitmqUtil rabbitmqUtil;

    @Autowired
    PostTagMapper postTagMapper;

    @Autowired
    PostMapper postMapper;

    @RabbitHandler
    public void review(Map<Object, Object> map, Channel channel, Message message) throws IOException {
        try {
            Post postEntity = JSONUtil.toBean((String) map.get("post"), Post.class);
            List<Long> tagUidList = JSONUtil.toList((String) map.get("tagUidList"), Long.class);
            boolean isUpdate = (boolean) map.get("isUpdate");
//
//            Boolean titleCheckResult = textCheckUtil.checkText(postEntity.getTitle());
//            Boolean contentCheckResult = textCheckUtil.checkText(postEntity.getContent());
//
//            // 根据审核结果进行不同的处理
//            if (titleCheckResult || contentCheckResult) {
//                containedBannedWordHandler(postEntity);
//            } else {
//                reviewSuccessHandler(postEntity, tagUidList, isUpdate);
//            }

            reviewSuccessHandler(postEntity, tagUidList, isUpdate);

            // 手动应答消息队列
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {

            if (Boolean.TRUE.equals(message.getMessageProperties().getRedelivered())) {

                log.error("文章审核失败,消息已重复处理,拒绝消息,内容:{}", map, e);
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {

                log.error("消息即将再次返回队列,内容:{}", map, e);
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }

    /**
     * 审核成功处理
     * @param postEntity 文章实体
     * @param tagUidList 标签id列表
     * @param isUpdate 是否更新
     */
    private void reviewSuccessHandler(Post postEntity, List<Long> tagUidList, boolean isUpdate) {
        postEntity.setStatus(PostConstant.NORMAL_STATUS);
        int result = postMapper.updateById(postEntity);
        if (result != 1) {
            throw new ServiceException("文章存入数据库失败");
        }
        //审核通过则更新文章
        if (Objects.equals(postEntity.getIsPublish(), PostConstant.PUBLISH)) {
            if (isUpdate) {
                rabbitmqUtil.transPost2ESUtil(postEntity);
            } else {
                rabbitmqUtil.saveEs(postEntity);
            }
        }
        tagUidList.forEach(t -> {
            PostTag postTag = new PostTag();
            postTag.setPostId(postEntity.getId());
            postTag.setTagId(t);
            postTagMapper.insert(postTag);
        });
    }

    /**
     * 审核失败处理
     * @param postEntity 文章实体
     */
    private void containedBannedWordHandler(Post postEntity) {
        postEntity.setStatus(PostConstant.INVISIBLE_STATUS);
        log.info("文章内容含有违禁词");
        int result = postMapper.updateById(postEntity);
        if (result != 1) {
            throw new ServiceException("文章存入数据库失败");
        }
    }

}
