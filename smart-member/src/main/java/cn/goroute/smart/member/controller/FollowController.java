package cn.goroute.smart.member.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.member.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Alickx
 * @Date: 2022/03/27/16:59
 * @Description: 用户关注控制器
 */
@RestController
@RequestMapping("/member/follow")
public class FollowController {

    @Autowired
    FollowService followService;

    @SaCheckLogin
    @PostMapping("/save")
    public Result followMember(@RequestBody String followMemberId) {
        return followService.saveFollow(followMemberId);
    }


    @GetMapping("/query")
    public Result queryFollow(@RequestParam String followMemberId) {
        return followService.queryFollow(followMemberId);
    }

}
