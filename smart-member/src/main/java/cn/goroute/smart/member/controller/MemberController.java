package cn.goroute.smart.member.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.entity.*;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.R;
import cn.goroute.smart.member.service.CollectService;
import cn.goroute.smart.member.service.MemberService;
import cn.goroute.smart.member.service.ThumbService;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
    private ThumbService thumbService;

    @Autowired
    private CollectService collectService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }

    @GetMapping("/list/post")
    public R getMemberInfoWithPost(@RequestParam String memberUid,
                                   @RequestParam Integer thumbType,
                                   @RequestParam String postUid) {

        QueryChainWrapper<MemberEntity> memberQueryChainWrapper = memberService.query().eq("uid", memberUid);
        MemberEntity member = memberService.getOne(memberQueryChainWrapper.getWrapper());
        MemberDTO memberDTO = new MemberDTO();
        BeanUtils.copyProperties(member, memberDTO);
        QueryChainWrapper<ThumbEntity> thumbEntityQueryChainWrapper = thumbService.query().eq("member_uid", memberUid).
                eq("thumb_type", thumbType).
                eq("to_uid", postUid);
        ThumbEntity thumbDTO = thumbService.getOne(thumbEntityQueryChainWrapper.getWrapper());
        QueryChainWrapper<CollectEntity> queryChainWrapper = collectService.query()
                .eq("member_uid", memberUid)
                .eq("post_uid", postUid);

        CollectEntity collectDTO = collectService.getOne(queryChainWrapper.getWrapper());

        return R.ok().put("authorInfo", memberDTO).
                put("isLike", thumbDTO != null).
                put("isCollect", collectDTO != null);

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
    public R getRole(){
        List<String> roleList = StpUtil.getRoleList();
        return R.ok().put("role",roleList);
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

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody String[] uids) {
        memberService.removeByIds(Arrays.asList(uids));

        return R.ok();
    }

}
