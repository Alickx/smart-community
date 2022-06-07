package cn.goroute.smart.member.controller;

import cn.goroute.smart.common.entity.vo.MemberBanSearchVO;
import cn.goroute.smart.common.entity.vo.MemberBanVO;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.member.service.MemberBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/05/23/20:24
 * @Description: 用户管理控制器
 */
@RestController
@RequestMapping("/member/manage")
public class MemberManagementController {

    @Autowired
    private MemberBanService memberBanService;

    /**
     * 封禁用户
     */
    @PostMapping("/ban")
    public Result bannedMembers(@RequestBody @Valid MemberBanVO memberBanVO){
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
     * 分页查询所有用户
     * @param
     * @return
     */
    @GetMapping("/list/query")
    public Result batchQueryMembers(@Valid MemberBanSearchVO memberBanSearchVO) {
        return memberBanService.batchQueryUsers(memberBanSearchVO);
    }

}
