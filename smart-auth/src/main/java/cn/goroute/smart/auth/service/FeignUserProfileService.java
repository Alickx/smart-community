package cn.goroute.smart.auth.service;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: Alickx
 * @Date: 2022/09/18/2:03
 * @Description:
 */
@FeignClient(name = "smart-user", path = "/user")
public interface FeignUserProfileService {



}
