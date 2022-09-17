package cn.goroute.smart.auth.entity;

import cn.goroute.smart.common.entity.dto.UserProfileDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/09/03/16:05
 * @Description: 登录成功返回实体类
 */
@Data
@Accessors(chain = true)
public class CustomUserDetails {

    /**
     * 通行token
     */
    private String accessToken;

    /**
     * 用戶信息
     */
    private UserProfileDto userProfileDto;

    /**
     * 权限列表
     */
    private List<String> permissions;

    /**
     * 角色列表
     */
    private List<String> roles;

}
