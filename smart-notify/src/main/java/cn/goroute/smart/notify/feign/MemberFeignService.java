package cn.goroute.smart.notify.feign;

import cn.goroute.smart.common.entity.dto.MemberDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/03/24/15:06
 * @Description:
 */
@FeignClient("smart-member")
public interface MemberFeignService {

    @GetMapping("member/member/getMemberByUid")
    MemberDTO getMemberByUid(@RequestParam String uid);

    @PostMapping("/member/member/list/post")
    List<MemberDTO> getInfoByMemberUids(@RequestBody List<String> memberUidList);

}