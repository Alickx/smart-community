package cn.goroute.smart.post.modules.article.mapper;

import cn.goroute.smart.post.domain.entity.UserInteractEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author llwst
* @description 针对表【user_interact(用户交互表)】的数据库操作Mapper
* @createDate 2023-02-10 01:00:04
* @Entity cn.goroute.smart.post.domain.UserInteract
*/
public interface UserInteractMapper extends BaseMapper<UserInteractEntity> {

	UserInteractEntity selectByUserIdAndTypeAndTargetId(@Param("userId") Long userId, @Param("type") Integer type, @Param("targetId") Long targetId);

	List<UserInteractEntity> batchGetUserPostInteract(@Param("targetIds") List<Long> targetIds, @Param("type") Integer type, @Param("userId") Long userId);
}




