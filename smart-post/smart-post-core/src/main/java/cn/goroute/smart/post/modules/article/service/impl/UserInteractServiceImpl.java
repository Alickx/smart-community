package cn.goroute.smart.post.modules.article.service.impl;

import cn.goroute.smart.common.constant.enums.BooleanEnum;
import cn.goroute.smart.post.domain.entity.CommentEntity;
import cn.goroute.smart.post.domain.entity.ThumbEntity;
import cn.goroute.smart.post.domain.entity.UserInteractEntity;
import cn.goroute.smart.post.modules.article.mapper.UserInteractMapper;
import cn.goroute.smart.post.modules.article.service.UserInteractService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author llwst
* @description 针对表【user_interact(用户交互表)】的数据库操作Service实现
* @createDate 2023-02-10 01:00:04
*/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserInteractServiceImpl extends ServiceImpl<UserInteractMapper, UserInteractEntity>
    implements UserInteractService{

	private final UserInteractMapper userInteractMapper;


	@Override
	public void updateThumbUserRelation(ThumbEntity thumbEntity, Boolean isThumb) {

		UserInteractEntity userInteractEntity = userInteractMapper
				.selectByUserIdAndTypeAndTargetId(thumbEntity.getUserId(), thumbEntity.getType(), thumbEntity.getToId());

		if (userInteractEntity == null ){
			userInteractEntity = new UserInteractEntity();
			userInteractEntity.setUserId(thumbEntity.getUserId());
			userInteractEntity.setType(thumbEntity.getType());
			userInteractEntity.setTargetId(thumbEntity.getToId());
			userInteractEntity.setIsThumb(isThumb ? BooleanEnum.TRUE.intValue() : BooleanEnum.FALSE.intValue());
			userInteractEntity.setIsComment(BooleanEnum.FALSE.intValue());
			userInteractEntity.setIsCollect(BooleanEnum.FALSE.intValue());
			userInteractMapper.insert(userInteractEntity);
		} else {
			userInteractEntity.setIsThumb(isThumb ? BooleanEnum.TRUE.intValue() : BooleanEnum.FALSE.intValue());
			userInteractMapper.updateById(userInteractEntity);
		}

	}

	@Override
	public void updateUserCommentRelation(CommentEntity commentEntity, Boolean isComment) {

		UserInteractEntity userInteractEntity = userInteractMapper
				.selectByUserIdAndTypeAndTargetId(commentEntity.getUserId(), commentEntity.getType(), commentEntity.getPostId());

		if (userInteractEntity == null ){
			userInteractEntity = new UserInteractEntity();
			userInteractEntity.setUserId(commentEntity.getUserId());
			userInteractEntity.setType(commentEntity.getType());
			userInteractEntity.setTargetId(commentEntity.getPostId());
			userInteractEntity.setIsThumb(BooleanEnum.FALSE.intValue());
			userInteractEntity.setIsComment(BooleanEnum.TRUE.intValue());
			userInteractEntity.setIsCollect(BooleanEnum.FALSE.intValue());
			userInteractMapper.insert(userInteractEntity);
		} else {
			userInteractEntity.setIsComment(isComment ? BooleanEnum.TRUE.intValue() : BooleanEnum.FALSE.intValue());
			userInteractMapper.updateById(userInteractEntity);
		}

	}

	@Override
	public List<UserInteractEntity> batchGetUserPostInteract(List<Long> targetIds, Integer type, Long userId) {

		return userInteractMapper.batchGetUserPostInteract(targetIds,type,userId);

	}
}




