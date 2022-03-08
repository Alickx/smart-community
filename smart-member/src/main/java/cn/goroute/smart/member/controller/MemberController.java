package cn.goroute.smart.member.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.entity.MemberDTO;
import cn.goroute.smart.common.entity.MemberEntity;
import cn.goroute.smart.common.utils.R;
import cn.goroute.smart.common.utils.RedisUtil;
import cn.goroute.smart.member.service.MemberService;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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
     * 列表
     */
    @GetMapping("/list/post")
    public List<MemberDTO> getMemberInfoWithPost(@RequestParam List<String> memberUidList) {

        List<MemberDTO> res = new ArrayList<>();

        MemberDTO memberDTO;
        for (String uid : memberUidList) {
            memberDTO = new MemberDTO();
            MemberEntity memberEntity = memberService.getById(uid);
            BeanUtils.copyProperties(memberEntity,memberDTO);
            res.add(memberDTO);
        }

        return res;
    }

    @GetMapping("/info/email}")
    public R infoMemberEmail(@RequestParam String email) {
        QueryChainWrapper<MemberEntity> emailQueryChainWrapper = memberService.query().eq("email", email);

        MemberEntity member = memberService.getOne(emailQueryChainWrapper.getWrapper());

        /*
          如果该邮箱已经注册则返回true，没注册则返回false
         */
        if (member == null) {
            return R.ok().put("data", false);
        } else {
            return R.ok().put("data", true);
        }
    }

    @GetMapping("/getRole")
    public R getRole() {
        List<String> roleList = StpUtil.getRoleList();
        return R.ok().put("role", roleList);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{uid}")
    public R info(@PathVariable("uid") String uid) {
        MemberEntity member = memberService.getById(uid);
        MemberDTO memberDTO = new MemberDTO();
        BeanUtils.copyProperties(Objects.requireNonNull(member), memberDTO);
        return R.ok().put("memberInfo", memberDTO);
    }

    /**
     * 微服务间调用
     */
    @GetMapping("/getMemberByUid")
    public MemberDTO getMemberByUid(@RequestParam String uid) {
        MemberEntity member = memberService.getById(uid);
        MemberDTO memberDTO = new MemberDTO();
        BeanUtils.copyProperties(Objects.requireNonNull(member), memberDTO);
        return memberDTO;
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody MemberEntity member) {
        memberService.save(member);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody MemberEntity member) {
        memberService.updateById(member);

        return R.ok();
    }


    @GetMapping("/logout")
    public R logout(){
        if (StpUtil.isLogin()) {
            StpUtil.logout();
        }
        return R.ok("登出成功!");
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody String[] uids) {
        memberService.removeByIds(Arrays.asList(uids));

        return R.ok();
    }

}
