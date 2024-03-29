package cn.goroute.smart.post.modules.article.async;

import cn.goroute.smart.post.domain.entity.PostEntity;
import cn.goroute.smart.post.modules.article.mapper.PostMapper;
import cn.goroute.smart.post.modules.article.mq.event.PostSyncEventMessageTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author: Alickx
 * @Date: 2023/02/13 10:59:37
 * @Description:
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class PostAsyncService {

    private final PostMapper postMapper;
    private final PostSyncEventMessageTemplate postSyncEventMessageTemplate;

    @Async
    public void getAndSendPostIndexEvent(Long id) {

        log.info("获取文章信息并同步es, postId:[{}]", id);

        PostEntity entity = postMapper.selectById(id);

        postSyncEventMessageTemplate.sendPostMessage(entity);
    }

}
