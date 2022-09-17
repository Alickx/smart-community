package cn.goroute.smart.member.service.impl;

import cn.goroute.smart.member.mapper.UserProfileMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.goroute.smart.member.domain.UserProfile;
import cn.goroute.smart.member.service.UserProfileService;
import org.springframework.stereotype.Service;

/**
* @author caiguopeng
* @description 针对表【user_profile(用户信息表)】的数据库操作Service实现
* @createDate 2022-09-17 19:30:44
*/
@Service
public class UserProfileServiceImpl extends ServiceImpl<UserProfileMapper, UserProfile>
    implements UserProfileService{

}




