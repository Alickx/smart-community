package cn.goroute.smart.post.service;

import cn.goroute.smart.post.domain.Comment;
import cn.goroute.smart.post.domain.Thumb;
import cn.goroute.smart.post.domain.UserInteract;
import com.hccake.extend.mybatis.plus.service.ExtendService;

import java.util.List;

/**
* @author llwst
* @description 针对表【user_interact(用户交互表)】的数据库操作Service
* @createDate 2023-02-10 01:00:04
*/
public interface UserInteractService extends ExtendService<UserInteract> {

	void updateThumbUserRelation(Thumb thumb, Boolean isThumb);

	void updateUserCommentRelation(Comment comment,Boolean isComment);

	List<UserInteract> batchGetUserPostInteract(List<Long> targetIds,Integer type, Long userId);
}

