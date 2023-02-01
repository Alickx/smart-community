package cn.goroute.smart.auth.service;

import cn.goroute.smart.auth.domain.AuthLoginLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hccake.extend.mybatis.plus.service.ExtendService;

/**
* @author caiguopeng
* @description 针对表【auth_login_log(登陆日志表)】的数据库操作Service
* @createDate 2022-09-17 19:21:37
*/
public interface AuthLoginLogService extends ExtendService<AuthLoginLog> {

}
