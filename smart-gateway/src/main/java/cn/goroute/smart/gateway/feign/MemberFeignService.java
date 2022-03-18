package cn.goroute.smart.gateway.feign;

import cn.goroute.smart.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("smart-member")
public interface MemberFeignService {

    @GetMapping("member/member/info/email")
    Result infoMemberEmail(@RequestParam String email);

}
