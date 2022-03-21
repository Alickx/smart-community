package cn.goroute.smart.product.feign;

import cn.goroute.smart.common.entity.vo.MemberPayConchVO;
import cn.goroute.smart.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Alickx
 * @Date: 2022/03/20/11:24
 * @Description: 用户服务feign接口
 */
@FeignClient("smart-member")
public interface MemberFeignService {

    @GetMapping("/member/conch/query_info")
    Result queryConchInfoByMemberUid(@RequestParam String uid);

    @PostMapping("/member/conch/pay")
    Result decrConchByPay(@RequestBody MemberPayConchVO memberPayConchVO);

}
