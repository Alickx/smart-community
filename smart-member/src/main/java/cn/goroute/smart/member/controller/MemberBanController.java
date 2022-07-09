package cn.goroute.smart.member.controller;

import cn.goroute.smart.common.entity.vo.MemberBanSearchVo;
import cn.goroute.smart.common.entity.vo.MemberBanVo;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.member.service.MemberBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/05/23/20:24
 * @Description: 用户Ban
 */
@RestController
@RequestMapping("/member/manage")
public class MemberBanController {

    @Autowired
    private MemberBanService memberBanService;

    /**
     * 封禁用户
     */
    @PostMapping("/ban")
    public Result bannedMembers(@RequestBody @Valid MemberBanVo memberBanVO){
        return memberBanService.banUser(memberBanVO);
    }

    /**
     *
     * @param banIds 解封id数组
     * @return 解封结果
     */
    @PostMapping("/ban/remove")
    public Result removeBannedMembers(@RequestBody List<String> banIds) {
        return memberBanService.removeBannedUsers(banIds);
    }

    /**
     * 查询用户所有的封禁项
     * @param memberUid 用户Uid
     * @return 封禁项列表
     */
    @GetMapping("/ban/query")
    public Result queryBannedMember(@RequestParam("memberUid") Long memberUid) {
        return memberBanService.queryBannedMember(memberUid);
    }

    /**
     * 分页查询所有用户
     * @param memberBanSearchVO 查询条件
     * @return 用户列表
     */
    @GetMapping("/list/query")
    public Result batchQueryBanMembers(@Valid MemberBanSearchVo memberBanSearchVO) {
        return memberBanService.batchQueryUsers(memberBanSearchVO);
    }

}
