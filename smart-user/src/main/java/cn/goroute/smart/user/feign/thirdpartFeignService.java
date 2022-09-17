package cn.goroute.smart.user.feign;

import cn.goroute.smart.common.entity.resp.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("smart-thirdpart")
public interface thirdpartFeignService {

    /**
     * @param emailAddress 邮箱地址
     * @return Result
     */
    @GetMapping("/sendEmailCaptcha")
    Response sendCaptcha(@RequestParam(value = "emailAddress") String emailAddress);
}
