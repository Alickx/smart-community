package cn.goroute.smart.notify.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.notify.service.EventRemindService;
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
    public Result queryUnreadCountNotification() {

        return eventRemindService.queryUnreadCountRemind();

    }

}
