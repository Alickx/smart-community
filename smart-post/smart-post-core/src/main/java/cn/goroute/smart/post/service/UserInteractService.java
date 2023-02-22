package cn.goroute.smart.post.service;

import cn.goroute.smart.post.domain.entity.CommentEntity;
import cn.goroute.smart.post.domain.entity.ThumbEntity;
import cn.goroute.smart.post.domain.entity.UserInteractEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author llwst
* @description 针对表【user_interact(用户交互表)】的数据库操作Service
* @createDate 2023-02-10 01:00:04
*/
public interface UserInteractService extends IService<UserInteractEntity> {

	void updateThumbUserRelation(ThumbEntity thumbEntity, Boolean isThumb);

	void updateUserCommentRelation(CommentEntity commentEntity, Boolean isComment);

	List<UserInteractEntity> batchGetUserPostInteract(List<Long> targetIds, Integer type, Long userId);
}

