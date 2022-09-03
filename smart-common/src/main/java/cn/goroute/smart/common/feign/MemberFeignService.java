package cn.goroute.smart.common.feign;

import cn.goroute.smart.common.entity.bo.MemberBo;
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
    @GetMapping("/member/getMemberByEmail")
    MemberBo getMemberByEmail(@RequestParam("email") String email);

    /**
     * 根据用户id获取用户信息
     * @param id 用户id
     * @return 用户信息
     */
    @GetMapping("/member/getMemberById")
    MemberBo getMemberById(@RequestParam("id") Long id);

    /**
     * 根据用户uid获取用户信息
     * @param memberUidList 用户uid列表
     * @return 用户信息列表
     */
    @PostMapping("/member/list")
    List<MemberBo> batchQueryUsers(@RequestBody List<Long> memberUidList);

    /**
     * 查询邮箱是否注册
     *
     * @param email 邮箱地址
     * @return true 已注册
     * false 未注册
     */
    @GetMapping("/member/isEmailRegistered")
    Boolean isEmailRegistered(@RequestParam String email);

    /**
     * 保存用户
     * @param memberBo 用户业务类
     * @return 保存结果
     */
    @PostMapping("/member/saveMember")
    Boolean saveMember(@RequestBody MemberBo memberBo);

    /**
     * 更新用户
     * @param memberBo 用户业务类
     * @return 更新结果
     */
    @PostMapping("/member/updateMember")
    Boolean updateMember(@RequestBody MemberBo memberBo);

}
