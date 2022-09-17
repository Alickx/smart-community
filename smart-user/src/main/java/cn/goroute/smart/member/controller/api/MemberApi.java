package cn.goroute.smart.member.controller.api;

import cn.goroute.smart.common.entity.bo.MemberBo;
import cn.goroute.smart.common.utils.ModelConverterUtils;
import cn.goroute.smart.member.entity.pojo.Member;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: Alickx
 * @Date: 2022/08/31/0:44
 * @Description:
 */
@RestController
public class MemberApi {

    @Resource
    private MemberService memberService;

    /**
     *
     * @param id id
     * @return 用户DTO实体类
     */
    @GetMapping("/member/getMemberById")
    public MemberBo getMemberById(@RequestParam("id") String id) {
        Member member = memberService.getById(id);
        MemberBo memberBo = new MemberBo();
        if (member != null) {
            BeanUtils.copyProperties(member, memberBo);
        }
        return memberBo;
    }

    /**
     * 保存用户
     * @param memberBo 用户业务类
     * @return 保存结果
     */
    @PostMapping("/member/saveMember")
    public Boolean saveMember(@RequestBody MemberBo memberBo) {
        Member member = new Member();
        ModelConverterUtils.copy(memberBo,member);
        memberService.save(member);
        return true;
    }

    /**
     * 查询邮箱是否注册
     *
     * @param email 邮箱地址
     * @return true 已注册
     * false 未注册
     */
    @GetMapping("/member/isEmailRegistered")
    public Boolean isEmailRegistered(@RequestParam String email) {
        Member member = memberService.getOne(new LambdaQueryWrapper<Member>().eq(Member::getEmail, email));

        // 如果为空，则说明该邮箱没有被注册
        return member == null;
    }

    /**
     * 更新用户
     * @param memberBo 用户业务类
     * @return 更新结果
     */
    @PostMapping("/member/updateMember")
    Boolean updateMember(@RequestBody MemberBo memberBo) {
        Member member = new Member();
        ModelConverterUtils.copy(memberBo,member);
        memberService.updateById(member);
        return true;
    }



}
