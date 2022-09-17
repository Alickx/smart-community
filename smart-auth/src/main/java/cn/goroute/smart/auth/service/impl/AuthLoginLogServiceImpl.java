package cn.goroute.smart.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.goroute.smart.auth.domain.AuthLoginLog;
import cn.goroute.smart.auth.service.AuthLoginLogService;
import cn.goroute.smart.auth.mapper.AuthLoginLogMapper;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author caiguopeng
* @description 针对表【auth_login_log(登陆日志表)】的数据库操作Service实现
* @createDate 2022-09-17 19:21:37
*/
@Service
public class AuthLoginLogServiceImpl extends ExtendServiceImpl<AuthLoginLogMapper, AuthLoginLog>
    implements AuthLoginLogService{

}




