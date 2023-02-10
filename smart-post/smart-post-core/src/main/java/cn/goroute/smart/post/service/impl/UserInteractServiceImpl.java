package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.post.domain.Comment;
import cn.goroute.smart.post.domain.Thumb;
import cn.goroute.smart.post.domain.UserInteract;
import cn.goroute.smart.post.mapper.UserInteractMapper;
import cn.goroute.smart.post.service.UserInteractService;
import com.hccake.ballcat.common.core.constant.enums.BooleanEnum;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
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
public class UserInteractServiceImpl extends ExtendServiceImpl<UserInteractMapper, UserInteract>
    implements UserInteractService{

	private final UserInteractMapper userInteractMapper;


	@Override
	public void updateThumbUserRelation(Thumb thumb,Boolean isThumb) {

		UserInteract userInteract = userInteractMapper
				.selectByUserIdAndTypeAndTargetId(thumb.getUserId(), thumb.getType(), thumb.getToId());

		if (userInteract == null ){
			userInteract = new UserInteract();
			userInteract.setUserId(thumb.getUserId());
			userInteract.setType(thumb.getType());
			userInteract.setTargetId(thumb.getToId());
			userInteract.setIsThumb(isThumb ? BooleanEnum.TRUE.getValue() : BooleanEnum.FALSE.getValue());
			userInteract.setIsComment(BooleanEnum.FALSE.getValue());
			userInteract.setIsCollect(BooleanEnum.FALSE.getValue());
			userInteractMapper.insert(userInteract);
		} else {
			userInteract.setIsThumb(isThumb ? BooleanEnum.TRUE.getValue() : BooleanEnum.FALSE.getValue());
			userInteractMapper.updateById(userInteract);
		}

	}

	@Override
	public void updateUserCommentRelation(Comment comment,Boolean isComment) {

		UserInteract userInteract = userInteractMapper
				.selectByUserIdAndTypeAndTargetId(comment.getUserId(), comment.getType(), comment.getPostId());

		if (userInteract == null ){
			userInteract = new UserInteract();
			userInteract.setUserId(comment.getUserId());
			userInteract.setType(comment.getType());
			userInteract.setTargetId(comment.getPostId());
			userInteract.setIsThumb(BooleanEnum.FALSE.getValue());
			userInteract.setIsComment(BooleanEnum.TRUE.getValue());
			userInteract.setIsCollect(BooleanEnum.FALSE.getValue());
			userInteractMapper.insert(userInteract);
		} else {
			userInteract.setIsComment(isComment ? BooleanEnum.TRUE.getValue() : BooleanEnum.FALSE.getValue());
			userInteractMapper.updateById(userInteract);
		}

	}

	@Override
	public List<UserInteract> batchGetUserPostInteract(List<Long> targetIds,Integer type, Long userId) {

		return userInteractMapper.batchGetUserPostInteract(targetIds,type,userId);

	}
}




