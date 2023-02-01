package cn.goroute.smart.search.handler.mq;

import cn.goroute.smart.post.domain.Post;
import cn.goroute.smart.search.service.PostIndexService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Author: Alickx
 * @Date: 2023/01/31/14:35
 * @Description:
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class PostSyncHandler {

	private final PostIndexService postIndexService;

	@EventListener(classes = Post.class)
	public void handle(Post post) {
		log.info("监听到文章id: [{}]，正在同步到ES", post.getId());
		postIndexService.save(post);
	}

}
