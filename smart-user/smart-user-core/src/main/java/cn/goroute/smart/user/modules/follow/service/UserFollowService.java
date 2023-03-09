package cn.goroute.smart.user.modules.follow.service;

import cn.goroute.smart.user.domain.entity.UserFollowEntity;
import cn.goroute.smart.user.domain.form.UserSaveFollowForm;
import cn.goroute.smart.user.domain.vo.UserFollowVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author caiguopeng
* @description 针对表【user_follow(用户关注表)】的数据库操作Service
* @createDate 2022-09-17 19:30:44
*/
public interface UserFollowService extends IService<UserFollowEntity> {

    Boolean saveFollow(UserSaveFollowForm userSaveFollowForm);

    List<UserFollowVO> queryUserFollow(Long userId);

    Boolean queryIsFollow(Long toUserId);
}
