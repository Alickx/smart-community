package cn.goroute.smart.post.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("smart-search")
public interface SearchFeignService {

    @GetMapping("search/delete")
    void deleteSearchPost(@RequestParam(value = "uid") Long uid);
}
