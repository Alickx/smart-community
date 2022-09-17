package cn.goroute.smart.member.service.impl;

import cn.goroute.smart.member.mapper.UserFollowMapper;
import cn.goroute.smart.member.service.UserFollowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.goroute.smart.member.domain.UserFollow;
import org.springframework.stereotype.Service;

/**
* @author caiguopeng
* @description 针对表【user_follow(用户关注表)】的数据库操作Service实现
* @createDate 2022-09-17 19:30:44
*/
@Service
public class UserFollowServiceImpl extends ServiceImpl<UserFollowMapper, UserFollow>
    implements UserFollowService {

}




