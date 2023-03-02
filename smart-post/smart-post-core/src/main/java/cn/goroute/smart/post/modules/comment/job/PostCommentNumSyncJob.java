package cn.goroute.smart.post.modules.comment.job;

import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.post.constant.PostRedisConstant;
import cn.goroute.smart.post.modules.article.mapper.PostMapper;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class PostCommentNumSyncJob {

	private final RedisUtil redisUtil;

	private final PostMapper postMapper;

	@XxlJob("postCommentNumSyncJobHandler")
	@Transactional(rollbackFor = Exception.class)
	public ReturnT<String> execute() {

		// 获取文章评论数缓存
		Map<Object, Object> map =
			redisUtil.hGetAll(PostRedisConstant.PostKey.POST_COMMENT_COUNT_KEY);

		XxlJobHelper.log("文章评论数缓存数量:{}", map.size());

		map.forEach((key,value) -> {
			// 获取文章ID
			String postId = key.toString();
			// 获取文章评论数
			String commentCount = value.toString();
			// 更新文章评论数
			postMapper.updateCommentCount(postId, Integer.parseInt(commentCount));
			// 删除文章评论数缓存
			redisUtil.hDelete(PostRedisConstant.PostKey.POST_COMMENT_COUNT_KEY, postId);
		});

		return ReturnT.SUCCESS;

	}

}
