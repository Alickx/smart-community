package cn.goroute.smart.post.modules.thumb.strategy.thumb.impl;

import cn.goroute.smart.post.domain.dto.ThumbDTO;
import cn.goroute.smart.post.domain.entity.CommentEntity;
import cn.goroute.smart.post.domain.entity.PostEntity;
import cn.goroute.smart.post.domain.entity.ThumbEntity;
import cn.goroute.smart.post.modules.comment.mapper.CommentMapper;
import cn.goroute.smart.post.modules.thumb.converter.ThumbConverter;
import cn.goroute.smart.post.modules.thumb.strategy.thumb.AbstractThumbStrategy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author: Alickx
 * @Date: 2023/01/04/18:21
 * @Description: 评论点赞策略
 */
@Component
public class CommentThumbStrategy extends AbstractThumbStrategy {
	@Resource
	private CommentMapper commentMapper;

	/**
	 * 点赞
	 *
	 * @param thumbEntity 点赞信息
	 * @return 是否点赞成功
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveThumb(ThumbEntity thumbEntity) {

		// 保存或更新点赞记录
		ThumbDTO thumbDTO = saveThumb2DB(thumbEntity);

		// 修改数据库
		commentMapper.incrThumbNum(thumbEntity.getToId(), 1);

		// 保存/更新用户关系
		userInteractService.updateThumbUserRelation(thumbEntity, true);

		// 发送事件
		if (thumbDTO != null) {
			sendThumbMqEvent(thumbDTO, true);
		}

	}

	/**
	 * 更新点赞记录
	 */
	private ThumbDTO saveThumb2DB(ThumbEntity thumbEntity) {

		Long toId = thumbEntity.getToId();

		CommentEntity commentEntity = commentMapper.selectById(toId);

		if (commentEntity == null) {
			return null;
		}

		// 是否是新点赞
		Boolean isNewThumb = saveThumbRecord(thumbEntity);

		if (!isNewThumb) {
			return null;
		}

		PostEntity postEntity = postMapper.selectById(commentEntity.getPostId());

		ThumbDTO thumbDTO = ThumbConverter.INSTANCE.poToDto(thumbEntity);
		thumbDTO.setPostId(commentEntity.getPostId());
		thumbDTO.setPostTitle(postEntity.getTitle());
		thumbDTO.setContent(commentEntity.getContent());
		thumbDTO.setToUserId(commentEntity.getUserId());

		return thumbDTO;

	}

	/**
	 * 取消点赞
	 *
	 * @param thumbEntity 点赞信息
	 * @return 是否取消点赞成功
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void cancelThumb(ThumbEntity thumbEntity) {

		// 逻辑删除点赞记录
		thumbMapper.deleteById(thumbEntity);

		// 修改数据库
		commentMapper.incrThumbNum(thumbEntity.getToId(), -1);

		// 保存/更新用户关系
		userInteractService.updateThumbUserRelation(thumbEntity, false);
	}
}
