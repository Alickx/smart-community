package cn.goroute.smart.member.feign;

import cn.goroute.smart.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("smart-thirdpart")
public interface thirdpartFeignService {

    @GetMapping("/sendEmailCaptcha")
    R sendCaptcha(@RequestParam String emailAddress);
}
