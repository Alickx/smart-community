package cn.goroute.smart.common.service.impl;

import cn.goroute.smart.common.service.AuthService;

import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/06/12/17:02
 * @Description: SpringSecurity的实现类
 */
public class AuthSpringSecurityServiceImpl implements AuthService {
    /**
     * 获取是否登录
     *
     * @return 是否登录 true：已登录 false：未登录
     */
    @Override
    public Boolean getIsLogin() {
        // 实现逻辑
        return null;
    }

    /**
     * 获取登录用户ID
     *
     * @return 登录用户ID
     */
    @Override
    public Long getLoginUid() {
        // 实现逻辑
        return null;
    }

    /**
     * 获取登录用户的角色列表
     *
     * @return 角色列表
     */
    @Override
    public List<String> getRoleList(Long memeberUid) {
        // 实现逻辑
        return null;
    }

    /**
     * 获取登录用户的权限列表
     *
     * @return 权限列表
     */
    @Override
    public List<String> getPermissionList() {
        // 实现逻辑
        return null;
    }

    /**
     * 注销用户登录
     *
     * @param memberUid 用户uid
     */
    @Override
    public void logOut(Long memberUid) {
    }
}
