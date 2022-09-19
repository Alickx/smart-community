package cn.goroute.smart.gateway.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.goroute.smart.gateway.service.FeignAuthService;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.ballcat.common.redis.core.annotation.Cached;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/09/18/0:36
 * @Description:
 */
@RequiredArgsConstructor
@Component
public class StpInterfaceImpl implements StpInterface{

    private final FeignAuthService feignAuthService;

    /**
     * 获取权限列表
     * @param loginId 登录id
     * @param loginType 登录类型
     * @return 权限列表
     */
    @Override
    @Cached(key = "permission:",keyJoint = "#userId", ttl = 2)
    public List<String> getPermissionList(Object loginId, String loginType) {
        R<List<String>> permission = feignAuthService.permission();
        return permission.getData();
    }

    /**
     * 获取角色列表
     * @param loginId 登录id
     * @param loginType 登录类型
     * @return 角色列表
     */
    @Override
    @Cached(key = "role:",keyJoint = "#userId", ttl = 2)
    public List<String> getRoleList(Object loginId, String loginType) {
        R<List<String>> role = feignAuthService.role();
        return role.getData();
    }
}
