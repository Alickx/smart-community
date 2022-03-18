package cn.goroute.smart.post.feign;

import cn.goroute.smart.common.entity.dto.MemberDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("smart-member")
public interface MemberFeignService {

    @GetMapping("member/member/getMemberByUid")
    MemberDTO getMemberByUid(@RequestParam String uid);

    @GetMapping("member/member/list/post")
    List<MemberDTO> getMemberInfoWithPost(@RequestParam List<String> memberUidList);
}
