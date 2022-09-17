package cn.goroute.smart.gateway.config;

import cn.dev33.satoken.stp.StpInterface;

import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/09/18/0:36
 * @Description:
 */
public class StpInterfaceImpl implements StpInterface{
    /**
     * 获取权限列表
     * @param loginId 登录id
     * @param loginType 登录类型
     * @return 权限列表
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return null;
    }

    /**
     * 获取角色列表
     * @param loginId 登录id
     * @param loginType 登录类型
     * @return 角色列表
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return null;
    }
}
