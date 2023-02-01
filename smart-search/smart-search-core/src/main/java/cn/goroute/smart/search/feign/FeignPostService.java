package cn.goroute.smart.search.feign;

import cn.goroute.smart.common.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: Alickx
 * @Date: 2023/02/01/16:02
 * @Description:
 */
@FeignClient(name = "smart-gateway"
        , path = "/api/v1/postApi"
        , configuration = FeignConfig.class)
public interface FeignPostService {




}
