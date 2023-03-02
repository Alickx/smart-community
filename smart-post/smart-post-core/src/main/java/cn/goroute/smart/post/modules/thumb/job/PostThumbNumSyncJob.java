package cn.goroute.smart.post.modules.thumb.job;

import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.post.constant.PostRedisConstant;
import cn.goroute.smart.post.modules.article.mapper.PostMapper;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PostThumbNumSyncJob {

	private final RedisUtil redisUtil;

	private final PostMapper postMapper;

	@XxlJob("postThumbNumSyncJobHandler")
	@Transactional(rollbackFor = Exception.class)
	public ReturnT<String> execute() {

		// 获取文章评论数缓存
		Map<Object, Object> map =
			redisUtil.hGetAll(PostRedisConstant.PostKey.POST_THUMB_COUNT_KEY);

		XxlJobHelper.log("文章点赞数缓存数量:{}", map.size());

		map.forEach((key,value) -> {
			// 获取文章ID
			String postId = key.toString();
			// 获取文章点赞数
			String thumbCount = value.toString();
			// 更新文章点赞数
			postMapper.updateThumbCount(postId, Integer.parseInt(thumbCount));
			// 删除文章点赞数缓存
			redisUtil.hDelete(PostRedisConstant.PostKey.POST_THUMB_COUNT_KEY, postId);
		});

		return ReturnT.SUCCESS;

	}

}
