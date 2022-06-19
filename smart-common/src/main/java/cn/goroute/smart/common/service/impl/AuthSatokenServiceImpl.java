package cn.goroute.smart.common.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/06/12/15:08
 * @Description: Satoken实现类
 */
@Service
public class AuthSatokenServiceImpl implements AuthService {

    /**
     * 获取是否登录
     *
     * @return 是否登录 true：已登录 false：未登录
     */
    @Override
    public Boolean getIsLogin() {
        return StpUtil.isLogin();
    }

    /**
     * 获取登录用户ID
     *
     * @return 登录用户ID
     */
    @Override
    public Long getLoginUid() {
        return StpUtil.getLoginIdAsLong();
    }

    /**
     * 获取登录用户的角色列表
     *
     * @return 角色列表
     */
    @Override
    public List<String> getRoleList(Long memberUid) {
//        List<String> roleList;
//        roleList = roleDao.getRoleNameByMemberUid(memberUid);
//        if (CollUtil.isEmpty(roleList)) {
//            return new ArrayList<>(0);
//        }
//        return roleList;
        return StpUtil.getRoleList();
    }

    /**
     * 获取登录用户的权限列表
     *
     * @return 权限列表
     */
    @Override
    public List<String> getPermissionList() {
        return StpUtil.getPermissionList();
    }

    /**
     * 注销用户登录
     *
     * @param memberUid 用户uid
     */
    @Override
    public void logOut(Long memberUid) {
        StpUtil.logout(memberUid);
    }
}
