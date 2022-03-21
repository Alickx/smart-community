package cn.goroute.smart.member.controller;

import cn.goroute.smart.common.entity.dto.MemberConchDTO;
import cn.goroute.smart.common.entity.pojo.MemberConchEntity;
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

    @GetMapping("/query_info")
    public Result queryConchInfoByMemberUid(@RequestParam String uid) {
        MemberConchEntity memberConchEntity = memberConchEntityService.getOne(new LambdaQueryWrapper<MemberConchEntity>()
                .eq(MemberConchEntity::getMemberUid, uid));
        MemberConchDTO memberConchDTO = new MemberConchDTO();

        if (memberConchEntity != null) {
            BeanUtils.copyProperties(memberConchEntity, memberConchDTO);
        }

        return Result.ok().put("data", memberConchDTO);
    }


    @PostMapping("/pay")
    public Result decrConchByPay(@RequestBody MemberPayConchVO memberPayConchVO){
        return memberConchEntityService.decrConchByPay(memberPayConchVO);
    }

}
