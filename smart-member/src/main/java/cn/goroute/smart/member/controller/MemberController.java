package cn.goroute.smart.member.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.service.AuthService;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.common.entity.dto.MemberDto;
import cn.goroute.smart.member.entity.pojo.Member;
import cn.goroute.smart.member.entity.vo.MemberInfoUpdateVo;
import cn.goroute.smart.member.service.MemberService;
import cn.goroute.smart.redis.util.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("smart/member")
@Slf4j
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    RedisUtil redisUtil;

    @Resource
    private AuthService authService;

    /**
     * 用户id集合查询用户信
     *
     * @param memberUidList uid集合
     * @return 用户信息集合
     */
    @PostMapping("/list")
    public List<MemberDto> batchQueryUsersByIdList(@RequestBody List<String> memberUidList) {

        List<MemberDto> res = new ArrayList<>(memberUidList.size());


        memberUidList.forEach(uid -> {
            Member member = memberService.getById(uid);
            if (Objects.nonNull(member)) {
                MemberDto memberDTO = new MemberDto();
                BeanUtils.copyProperties(member, memberDTO);
                res.add(memberDTO);
            }
        });

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
        Member member = memberService.getOne(new LambdaQueryWrapper<Member>().eq(Member::getEmail, email));

        // 如果为空，则说明该邮箱没有被注册
        if (member == null) {
            return Result.ok();
        } else {
            return Result.error();
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
            MemberDto memberDTO = new MemberDto();
            BeanUtils.copyProperties(Objects.requireNonNull(member), memberDTO);
            return Result.ok().put("data", memberDTO);
        } else {
            return Result.error("该用户不存在!");
        }
    }

    /**
     * 微服务间调用
     *
     * @param uid 用户uid
     * @return 用户DTO实体类
     */
    @GetMapping("/getMemberByUid")
    public MemberDto getMemberByUid(@RequestParam String uid) {
        Member member = memberService.getById(uid);
        MemberDto memberDTO = new MemberDto();
        if (member != null) {
            BeanUtils.copyProperties(member, memberDTO);
        }
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
    public Result update(@RequestBody MemberInfoUpdateVo memberInfoUpdateVO) {

        return memberService.updateMemberInfo(memberInfoUpdateVO);
    }

    @PostMapping("/logout")
    public Result logout() {
        Boolean isLogin = authService.getIsLogin();
        if (Boolean.TRUE.equals(isLogin)) {
            authService.logOut(authService.getLoginUid());
            return Result.ok();
        }
        return Result.error("用户未登录");
    }

    @GetMapping("/isLogin")
    public Result isLogin() {
        Boolean isLogin = authService.getIsLogin();
        if (Boolean.TRUE.equals(isLogin)) {
            return Result.ok();
        }
        return Result.error("用户未登录");
    }



}
