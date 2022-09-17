package cn.goroute.smart.member.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.goroute.smart.common.entity.resp.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Alickx
 * @Date: 2022/03/27/16:59
 * @Description: 用户关注控制器
 */
@RestController
@RequestMapping("smart/member/follow")
@Api(tags = "用户关注")
public class FollowController {

    @Autowired
    FollowService followService;

    @SaCheckLogin
    @PostMapping("/save")
    @ApiOperation(value = "关注用户", notes = "关注用户", httpMethod = "POST")
    @ApiParam(name = "followMemberId", value = "用户id", required = true)
    public Response followMember(@RequestBody Long followMemberId) {
        return followService.saveFollow(followMemberId);
    }


    @GetMapping("/query")
    @ApiOperation(value = "查询关注用户", notes = "查询关注用户", httpMethod = "GET")
    @ApiParam(name = "followMemberId", value = "用户id", required = true)
    public Response queryFollow(@RequestParam Long followMemberId) {
        return followService.queryFollow(followMemberId);
    }

}
