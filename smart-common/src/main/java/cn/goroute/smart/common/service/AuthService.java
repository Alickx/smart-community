package cn.goroute.smart.common.service;

import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/06/12/15:07
 * @Description: 权限服务接口
 */
public interface AuthService {

    /**
     * 获取是否登录
     * @return 是否登录 true：已登录 false：未登录
     */
    Boolean getIsLogin();

    /**
     * 获取登录用户ID
     * @return 登录用户ID
     */
    Long getLoginUid();

    /**
     * 获取指定用户的角色列表
     * @return 角色列表
     */
    List<String> getRoleList(Long memberUid);

    /**
     * 获取登录用户的权限列表
     * @return 权限列表
     */
    List<String> getPermissionList();

    /**
     * 注销用户登录
     * @param memberUid 用户uid
     */
    void logOut(Long memberUid);
}
