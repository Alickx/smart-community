package cn.goroute.smart.notify.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.notify.service.EventRemindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Alickx
 * @Date: 2022/03/24/16:50
 * @Description:
 */
@RestController
@RequestMapping("smart/notification")
@Api(tags = "通知服务")
public class NotificationController {

    @Autowired
    private EventRemindService eventRemindService;

    /**
     * 查询未读事件通知总数量
     *
     * @return 未读事件通知总数量
     */
    @SaCheckLogin
    @GetMapping("/count/unread")
    @ApiOperation(value = "查询未读事件通知总数量", notes = "查询未读事件通知总数量",httpMethod = "GET")
    public Response queryUnreadCountNotification() {

        return eventRemindService.queryUnreadCountRemind();

    }

}
