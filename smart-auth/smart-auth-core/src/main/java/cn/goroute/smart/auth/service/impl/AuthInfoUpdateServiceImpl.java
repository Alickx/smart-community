package cn.goroute.smart.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.goroute.smart.auth.domain.AuthInfoUpdate;
import cn.goroute.smart.auth.service.AuthInfoUpdateService;
import cn.goroute.smart.auth.mapper.AuthInfoUpdateMapper;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author caiguopeng
* @description 针对表【auth_info_update(用户注册日志表)】的数据库操作Service实现
* @createDate 2022-09-17 19:21:37
*/
@Service
public class AuthInfoUpdateServiceImpl extends ExtendServiceImpl<AuthInfoUpdateMapper, AuthInfoUpdate>
    implements AuthInfoUpdateService{

}




