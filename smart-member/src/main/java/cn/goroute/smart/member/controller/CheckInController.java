package cn.goroute.smart.member.controller;

import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.member.service.CheckInService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("smart/member")
@Api(tags = "签到")
public class CheckInController {

    @Autowired
    CheckInService checkInService;

    /**
     * 签到处理
     * @return 签到结果
     */
    @PostMapping("/checkIn")
    @ApiOperation(value = "签到处理", notes = "签到处理", httpMethod = "POST")
    public Response checkIn() {
        return checkInService.checkIn();
    }

    /**
     * 获取这个月签到数据
     * @return
     */
    @GetMapping("/getCheckInInfo")
    @ApiOperation(value = "获取签到数据", notes = "获取签到数据", httpMethod = "GET")
    public Response getCheckInInfo() {
        return checkInService.getCheckInInfo();
    }


}
