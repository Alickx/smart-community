package cn.goroute.smart.post.modules.article.job;

import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.post.constant.PostRedisConstant;
import cn.goroute.smart.post.domain.PostExpandInfoEntity;
import cn.goroute.smart.post.modules.article.service.PostExpandInfoEntityService;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class PostExpandInfoSyncJob {

	private final RedisUtil redisUtil;

	private final PostExpandInfoEntityService postExpandInfoEntityService;

	@XxlJob("postExpandInfoSyncJob")
	public ReturnT<String> execute() {

		Set<String> postIds = redisUtil.setMembers(PostRedisConstant.PostKey.POST_EXPAND_INFO_UPDATE_LIST_KEY);
		if (CollUtil.isEmpty(postIds)) {
			XxlJobHelper.log("没有需要同步的文章拓展信息");
			return ReturnT.SUCCESS;
		}

		for (String postId : postIds) {

			String redisKey = PostRedisConstant.PostKey.POST_EXPAND_INFO_KEY + ":" + postId;
			Map<String, String> map = redisUtil.hGetAll(redisKey);

			if (map.isEmpty()) {
				XxlJobHelper.log("文章拓展信息缓存中不存在，文章id：{}", postId);
				redisUtil.delete(redisKey);
				continue;
			}

			// 更新数据库
			PostExpandInfoEntity entity = PostExpandInfoEntity.builder()
				.postId(Long.parseLong(postId))
				.commentCount(Integer.parseInt(map.get(PostExpandInfoEntity.Fields.commentCount)))
				.thumbCount(Integer.parseInt(map.get(PostExpandInfoEntity.Fields.thumbCount)))
				.collectCount(Integer.parseInt(map.get(PostExpandInfoEntity.Fields.collectCount)))
				.viewCount(Integer.parseInt(map.get(PostExpandInfoEntity.Fields.viewCount)))
				.build();

			LambdaUpdateWrapper<PostExpandInfoEntity> updateWrapper = new LambdaUpdateWrapper<>();
			updateWrapper.eq(PostExpandInfoEntity::getPostId, entity.getPostId());
			postExpandInfoEntityService.update(entity,updateWrapper);
		}

		// 清空缓存
		redisUtil.delete(PostRedisConstant.PostKey.POST_EXPAND_INFO_UPDATE_LIST_KEY);

		XxlJobHelper.log("文章拓展信息同步完成,共同步{}条数据", postIds.size());

		return ReturnT.SUCCESS;

	}

}
