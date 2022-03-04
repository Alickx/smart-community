package cn.goroute.smart.thirdpart.feign;

import cn.goroute.smart.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("smart-member")
public interface MemberFeignService {

    @GetMapping("member/member/info/email}")
    R infoMemberEmail(@RequestParam String email);

}
