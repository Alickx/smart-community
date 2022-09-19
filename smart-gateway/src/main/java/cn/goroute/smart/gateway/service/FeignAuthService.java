package cn.goroute.smart.gateway.service;

import com.hccake.ballcat.common.model.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/09/19/0:55
 * @Description: Feign 认证授权中心
 */
@FeignClient(name = "smart-gateway", path = "/api/v1/auth")
public interface FeignAuthService {

    /**
     * 获取当前用户的权限
     * @return R<List<String>> 权限列表
     */
    @GetMapping("/permission")
    R<List<String>> permission();

    /**
     * 获取当前用户的角色
     * @return R<List<String>> 角色列表
     */
    @GetMapping("/role")
    R<List<String>> role();

}
