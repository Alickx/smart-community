package cn.goroute.smart.member.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.entity.dto.MemberDTO;
import cn.goroute.smart.common.entity.pojo.Member;
import cn.goroute.smart.common.entity.vo.MemberInfoUpdateVO;
import cn.goroute.smart.common.api.ResultCode;
import cn.goroute.smart.common.utils.RedisUtil;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.member.service.MemberService;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * 用户信息表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:45:33
 */
@RestController
@RequestMapping("member/member")
@Slf4j
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    RedisUtil redisUtil;

    /**
     * 用户id集合查询用户信
     *
     * @param memberUidList uid集合
     * @return 用户信息集合
     */
    @PostMapping("/list")
    public List<MemberDTO> batchQueryUsers(@RequestBody List<String> memberUidList) {

        List<MemberDTO> res = new ArrayList<>();

        MemberDTO memberDTO;
        for (String uid : memberUidList) {
            memberDTO = new MemberDTO();
            Member member = memberService.getById(uid);
            if (member != null) {
                BeanUtils.copyProperties(member, memberDTO);
            }
            res.add(memberDTO);
        }

        return res;
    }

    /**
     * 查询邮箱是否注册
     *
     * @param email 邮箱地址
     * @return true 已注册
     * false 未注册
     */
    @GetMapping("/info/email")
    public Result queryUserEmail(@RequestParam String email) {
        QueryChainWrapper<Member> emailQueryChainWrapper = memberService.query()
                .eq("email", email);

        Member member = memberService.getOne(emailQueryChainWrapper.getWrapper());
        /*
          如果该邮箱已经注册则返回true，没注册则返回false
         */
        if (member == null) {
            return Result.ok().put("data", false);
        } else {
            return Result.ok().put("data", true);
        }
    }

    /**
     * 查询用户的角色
     *
     * @return 用户角色列表
     */
    @GetMapping("/getRole")
    public Result getRole() {
        List<String> roleList = StpUtil.getRoleList();
        return Result.ok().put("data", roleList);
    }

    /**
     * 获取用户信息
     *
     * @param uid 用户uid
     * @return 用户信息
     */
    @GetMapping("/info/{uid}")
    public Result info(@PathVariable("uid") String uid) {
        Member member = memberService.getById(uid);
        if (member != null) {
            MemberDTO memberDTO = new MemberDTO();
            BeanUtils.copyProperties(Objects.requireNonNull(member), memberDTO);
            return Result.ok().put("data", memberDTO);
        } else {
            return Result.error(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage());
        }
    }

    /**
     * 微服务间调用
     *
     * @param uid 用户uid
     * @return 用户DTO实体类
     */
    @GetMapping("/getMemberByUid")
    public MemberDTO getMemberByUid(@RequestParam String uid) {
        Member member = memberService.getById(uid);
        MemberDTO memberDTO = new MemberDTO();
        BeanUtils.copyProperties(Objects.requireNonNull(member), memberDTO);
        return memberDTO;
    }

    /**
     * 更新用户信息
     *
     * @param memberInfoUpdateVO 用户信息vo
     * @return 更新后的用户信息
     */
    @SaCheckLogin
    @PostMapping("/profile/update")
    public Result update(@RequestBody MemberInfoUpdateVO memberInfoUpdateVO) {

        return memberService.updateMemberInfo(memberInfoUpdateVO);
    }

}
