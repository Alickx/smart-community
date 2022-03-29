package cn.goroute.smart.member.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.goroute.smart.common.entity.dto.MemberConchDTO;
import cn.goroute.smart.common.entity.pojo.MemberConch;
import cn.goroute.smart.common.entity.vo.MemberPayConchVO;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.member.service.MemberConchEntityService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Alickx
 * @Date: 2022/03/20/9:31
 * @Description: 用户贝壳控制类
 */
@RestController
@RequestMapping("/member/conch")
public class ConchController {

    @Autowired
    MemberConchEntityService memberConchEntityService;

    @SaCheckLogin
    @GetMapping("/query_info")
    public Result queryConchInfoByMemberUid(@RequestParam String uid) {
        MemberConch memberConch = memberConchEntityService.getOne(new LambdaQueryWrapper<MemberConch>()
                .eq(MemberConch::getMemberUid, uid));
        MemberConchDTO memberConchDTO = new MemberConchDTO();

        if (memberConch != null) {
            BeanUtils.copyProperties(memberConch, memberConchDTO);
        }

        return Result.ok().put("data", memberConchDTO);
    }

    @SaCheckLogin
    @PostMapping("/pay")
    public Result decrConchByPay(@RequestBody MemberPayConchVO memberPayConchVO){
        return memberConchEntityService.decrConchByPay(memberPayConchVO);
    }

}
