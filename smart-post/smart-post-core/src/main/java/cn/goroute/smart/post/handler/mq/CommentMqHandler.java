package cn.goroute.smart.post.handler.mq;

import cn.goroute.smart.post.domain.Comment;
import cn.goroute.smart.post.mapper.PostMapper;
import cn.goroute.smart.post.service.UserInteractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Alickx
 * @Date: 2023/01/07/11:47
 * @Description:
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class CommentMqHandler {

    private final PostMapper postMapper;

	private final UserInteractService userInteractService;

    @EventListener(classes = Comment.class)
	@Transactional(rollbackFor = Exception.class)
    public void handle(Comment comment) {

        log.info("评论消息队列监听者监听到消息:[{}]", comment);

		// 更新文章评论数
		postMapper.incrCommentCount(comment.getPostId());

		// 更新用户文章关系
		userInteractService.updateUserCommentRelation(comment,true);

    }


}
