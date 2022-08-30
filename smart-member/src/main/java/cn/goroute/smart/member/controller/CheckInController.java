package cn.goroute.smart.member.controller;

import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.member.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Alickx
 * @Date: 2022/07/09/11:31
 * @Description: 签到
 */
@RestController
@RequestMapping("smart/member/checkin")
public class CheckInController {

    @Autowired
    CheckInService checkInService;

    /**
     * 签到处理
     * @return 签到结果
     */
    @PostMapping
    public Result checkIn() {
        return checkInService.checkIn();
    }

    /**
     * 获取这个月签到数据
     * @return
     */
    @GetMapping("/getCheckInInfo")
    public Result getCheckInInfo() {
        return checkInService.getCheckInInfo();
    }


}
