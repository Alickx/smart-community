package cn.goroute.smart.user.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.entity.dto.UserProfileDto;
import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.common.service.AuthService;
import cn.goroute.smart.user.entity.pojo.Member;
import cn.goroute.smart.user.entity.vo.MemberInfoUpdateVo;
import cn.goroute.smart.redis.util.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "用户信息")
public class UserProfileController {
    @Autowired
    private MemberService memberService;

    @Autowired
    RedisUtil redisUtil;

    @Resource
    private AuthService authService;

    /**
     * 用户id集合查询用户信
     *
     * @param memberIdList uid集合
     * @return 用户信息集合
     */
    @PostMapping("/list")
    @ApiOperation(value = "用户id集合查询用户信息", notes = "用户id集合查询用户信息", response = Member.class, responseContainer = "List")
    public List<UserProfileDto> batchQueryUsersByIdList(@RequestBody List<String> memberIdList) {

        List<UserProfileDto> res = new ArrayList<>(memberIdList.size());
        memberIdList.forEach(uid -> {
            Member member = memberService.getById(uid);
            if (Objects.nonNull(member)) {
                UserProfileDto userProfileDTO = new UserProfileDto();
                BeanUtils.copyProperties(member, userProfileDTO);
                res.add(userProfileDTO);
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
    @GetMapping("/isEmailRegistered")
    @ApiOperation(value = "查询邮箱是否注册", notes = "查询邮箱是否注册", response = Boolean.class)
    @ApiParam(name = "email", value = "邮箱地址", required = true)
    public Response isEmailRegistered(@RequestParam String email) {
        Member member = memberService.getOne(new LambdaQueryWrapper<Member>().eq(Member::getEmail, email));

        // 如果为空，则说明该邮箱没有被注册
        if (member == null) {
            return Response.success();
        } else {
            return Response.failure();
        }
    }

    /**
     * 查询用户的角色
     *
     * @return 用户角色列表
     */
    @ApiOperation(value = "查询用户的角色", notes = "查询用户的角色", response = String.class, responseContainer = "List")
    @GetMapping("/getRole")
    public Response getRole() {
        List<String> roleList = StpUtil.getRoleList();
        return Response.success(roleList);
    }

    /**
     * 获取用户信息
     *
     * @param uid 用户uid
     * @return 用户信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", response = Member.class)
    @ApiParam(name = "id", value = "用户uid", required = true)
    public Response info(@PathVariable("id") String uid) {
        Member member = memberService.getById(uid);
        if (member != null) {
            UserProfileDto userProfileDTO = new UserProfileDto();
            BeanUtils.copyProperties(Objects.requireNonNull(member), userProfileDTO);
            return Response.success(userProfileDTO);
        } else {
            return Response.failure("该用户不存在!");
        }
    }

    /**
     * 更新用户信息
     *
     * @param memberInfoUpdateVO 用户信息vo
     * @return 更新后的用户信息
     */
    @SaCheckLogin
    @PostMapping("/profile/update")
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息", response = Member.class,httpMethod = "POST")
    @ApiParam(name = "memberInfoUpdateVO", value = "用户信息vo", required = true)
    public Response update(@RequestBody MemberInfoUpdateVo memberInfoUpdateVO) {

        return memberService.updateMemberInfo(memberInfoUpdateVO);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "退出登录", httpMethod = "POST")
    public Response logout() {
        Boolean isLogin = authService.getIsLogin();
        if (Boolean.TRUE.equals(isLogin)) {
            authService.logOut(authService.getLoginUid());
            return Response.success();
        }
        return Response.failure("用户未登录");
    }

    @GetMapping("/isLogin")
    @ApiOperation(value = "查询用户是否登录", notes = "查询用户是否登录", response = Boolean.class,httpMethod = "GET")
    public Response isLogin() {
        boolean isLogin = authService.getIsLogin();
        if (Boolean.TRUE.equals(isLogin)) {
            return Response.success();
        }
        return Response.failure();
    }



}
