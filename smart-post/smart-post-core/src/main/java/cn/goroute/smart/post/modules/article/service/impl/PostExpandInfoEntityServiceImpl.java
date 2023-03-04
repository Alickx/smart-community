package cn.goroute.smart.post.modules.article.service.impl;

import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.post.constant.PostRedisConstant;
import cn.goroute.smart.post.domain.PostExpandInfoEntity;
import cn.goroute.smart.post.modules.article.converter.PostExpandInfoConverter;
import cn.goroute.smart.post.modules.article.mapper.PostExpandInfoEntityMapper;
import cn.goroute.smart.post.modules.article.service.PostExpandInfoEntityService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Alickx
 * @description 针对表【t_post_expand_info(文章拓展信息)】的数据库操作Service实现
 * @createDate 2023-03-04 11:12:47
 */
@RequiredArgsConstructor
@Service
public class PostExpandInfoEntityServiceImpl extends ServiceImpl<PostExpandInfoEntityMapper, PostExpandInfoEntity>
	implements PostExpandInfoEntityService {

	private final RedisUtil redisUtil;

	/**
	 * 查询文章拓展信息
	 *
	 * @param postIds 文章id
	 * @return 文章拓展信息
	 */
	@Override
	public List<PostExpandInfoEntity> batchPostExpandInfo(List<Long> postIds) {

		List<PostExpandInfoEntity> result = Lists.newArrayList();
		// 缓存中没有的，批量到数据库查询
		List<Long> dbQueryPostIds = Lists.newArrayList();

		for (Long postId : postIds) {

			String redisKey = PostRedisConstant.PostKey.POST_EXPAND_INFO_KEY + ":" + postId;
			Map<Object, Object> postExpandInfoMap = redisUtil.hGetAll(redisKey);

			if (postExpandInfoMap.isEmpty()) {
				dbQueryPostIds.add(postId);
				continue;
			}

			PostExpandInfoEntity postExpandInfoEntity = PostExpandInfoConverter.INSTANCE.mapToVo(postExpandInfoMap);
			result.add(postExpandInfoEntity);
		}

		// 查询数据库
		LambdaQueryWrapper<PostExpandInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(PostExpandInfoEntity::getPostId, dbQueryPostIds);

		List<PostExpandInfoEntity> postExpandInfoEntities = this.list(queryWrapper);

		result.addAll(postExpandInfoEntities);

		return result;
	}
}




