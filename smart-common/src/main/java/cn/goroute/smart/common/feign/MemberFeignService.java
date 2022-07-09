package cn.goroute.smart.common.feign;

import cn.goroute.smart.common.entity.dto.MemberDto;
import cn.goroute.smart.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Alickx
 */
@FeignClient("smart-member")
public interface MemberFeignService {

    /**
     * 根据用户邮箱获取用户信息
     * @param email 用户邮箱
     * @return 邮箱地址对应的用户信息
     */
    @GetMapping("member/member/info/email")
    Result queryUserEmail(@RequestParam(value = "email") String email);

    /**
     * 根据用户uid获取用户信息
     * @param uid 用户uid
     * @return 用户信息
     */
    @GetMapping("member/member/getMemberByUid")
    MemberDto getMemberByUid(@RequestParam(value = "uid") Long uid);

    /**
     * 根据用户uid获取用户信息
     * @param memberUidList 用户uid列表
     * @return 用户信息列表
     */
    @PostMapping("/member/member/list")
    List<MemberDto> batchQueryUsers(@RequestBody List<Long> memberUidList);

}
