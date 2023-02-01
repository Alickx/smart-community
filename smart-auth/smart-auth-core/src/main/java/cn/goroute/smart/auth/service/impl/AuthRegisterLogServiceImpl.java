package cn.goroute.smart.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.goroute.smart.auth.domain.AuthRegisterLog;
import cn.goroute.smart.auth.service.AuthRegisterLogService;
import cn.goroute.smart.auth.mapper.AuthRegisterLogMapper;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author caiguopeng
* @description 针对表【auth_register_log(用户注册日志表)】的数据库操作Service实现
* @createDate 2022-09-17 19:21:37
*/
@Service
public class AuthRegisterLogServiceImpl extends ExtendServiceImpl<AuthRegisterLogMapper, AuthRegisterLog>
    implements AuthRegisterLogService{

}




