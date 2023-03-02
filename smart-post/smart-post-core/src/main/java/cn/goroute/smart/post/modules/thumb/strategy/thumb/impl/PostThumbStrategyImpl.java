package cn.goroute.smart.post.modules.thumb.strategy.thumb.impl;

import cn.goroute.smart.common.constant.enums.ErrorCodeEnum;
import cn.goroute.smart.common.exception.BusinessException;
import cn.goroute.smart.common.util.RedisUtil;
import cn.goroute.smart.post.constant.PostRedisConstant;
import cn.goroute.smart.post.modules.thumb.converter.ThumbConverter;
import cn.goroute.smart.post.domain.dto.ThumbDTO;
import cn.goroute.smart.post.domain.entity.PostEntity;
import cn.goroute.smart.post.domain.entity.ThumbEntity;
import cn.goroute.smart.post.modules.article.mapper.PostMapper;
import cn.goroute.smart.post.modules.thumb.strategy.thumb.AbstractThumbStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/10/22/16:41
 * @Description:
 */
@Component("postThumbStrategy")
public class PostThumbStrategyImpl extends AbstractThumbStrategy {


	@Resource
	private PostMapper postMapper;
	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 点赞
	 *
	 * @param thumbEntity 点赞信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveThumb(ThumbEntity thumbEntity) {

		// 保存点赞记录
		ThumbDTO thumbDTO = saveThumb2DB(thumbEntity);

		// 更新文章点赞数缓存
		redisUtil.hIncrBy(PostRedisConstant.PostKey.POST_THUMB_COUNT_KEY, thumbEntity.getToId().toString(), 1);

		// 保存/更新用户关系
		userInteractService.updateThumbUserRelation(thumbEntity, true);

		if (null != thumbDTO) {
			// 发送事件
			sendThumbMqEvent(thumbDTO, true);
		}
	}

	private ThumbDTO saveThumb2DB(ThumbEntity thumbEntity) {

		Long toId = thumbEntity.getToId();

		PostEntity postEntity = postMapper.selectById(toId);

		if (postEntity == null) {
			throw new BusinessException(ErrorCodeEnum.PARAM_ERROR);
		}

		// 是否是新点赞
		Boolean isNewThumb = saveThumbRecord(thumbEntity);

		if (!isNewThumb) {
			return null;
		}

		ThumbDTO thumbDTO = ThumbConverter.INSTANCE.poToDto(thumbEntity);
		thumbDTO.setPostTitle(postEntity.getTitle());
		thumbDTO.setPostId(postEntity.getId());
		thumbDTO.setContent(postEntity.getTitle());
		thumbDTO.setToUserId(postEntity.getAuthorId());

		return thumbDTO;

	}


	/**
	 * 取消点赞
	 *
	 * @param thumbEntity 点赞信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void cancelThumb(ThumbEntity thumbEntity) {

		long toId = thumbEntity.getToId();

		// 逻辑删除记录
		thumbMapper.deleteById(thumbEntity);

		// 更新文章点赞数缓存
		redisUtil.hIncrBy(PostRedisConstant.PostKey.POST_THUMB_COUNT_KEY, toId, -1);

		// 保存/更新用户关系
		userInteractService.updateThumbUserRelation(thumbEntity, false);
	}
}
