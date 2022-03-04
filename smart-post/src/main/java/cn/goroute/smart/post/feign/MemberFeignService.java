package cn.goroute.smart.post.feign;

import cn.goroute.smart.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("smart-member")
public interface MemberFeignService {

    @RequestMapping("member/member/info/{uid}")
    R info(@PathVariable("uid") String uid);

    @GetMapping("member/member/list/post")
    R getMemberInfoWithPost(@RequestParam String memberUid,
                            @RequestParam Integer thumbType,
                            @RequestParam String postUid);
}
