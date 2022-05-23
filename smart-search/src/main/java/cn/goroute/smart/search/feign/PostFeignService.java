package cn.goroute.smart.search.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("smart-post")
public interface PostFeignService {

    @GetMapping("/post/thumb/is_like")
    Boolean isLike(@RequestParam(value = "loginUid") Long loginIdAsString, @RequestParam(value = "uid") Long uid);

}
