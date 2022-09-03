package cn.goroute.smart.auth.entity.dto;

import cn.goroute.smart.common.entity.dto.MemberDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/09/03/16:05
 * @Description: 登录成功返回信息
 */
@Data
@Accessors(chain = true)
public class AuthLoginSuccessDto {

    /**
     * 通行token
     */
    private String accessToken;

    /**
     * 用戶信息
     */
    private MemberDto userInfo;

    /**
     * 权限列表
     */
    private List<String> permissions;

    /**
     * 角色列表
     */
    private List<String> roles;

}
