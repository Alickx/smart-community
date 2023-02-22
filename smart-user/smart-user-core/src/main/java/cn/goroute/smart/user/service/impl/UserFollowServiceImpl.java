package cn.goroute.smart.user.service.impl;

import cn.goroute.smart.user.mapper.UserFollowMapper;
import cn.goroute.smart.user.service.UserFollowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.goroute.smart.user.domain.entity.UserFollowEntity;
import org.springframework.stereotype.Service;

/**
* @author caiguopeng
* @description 针对表【user_follow(用户关注表)】的数据库操作Service实现
* @createDate 2022-09-17 19:30:44
*/
@Service
public class UserFollowServiceImpl extends ServiceImpl<UserFollowMapper, UserFollowEntity>
    implements UserFollowService {

}




