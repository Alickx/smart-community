package cn.goroute.smart.notify.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.goroute.smart.common.utils.QueryParam;
import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.notify.service.EventRemindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Alickx
 * @Date: 2022/03/26/10:52
 * @Description: 事件提醒控制器
 */
@RestController
@RequestMapping("smart/notification")
@Api(tags = "事件通知")
public class EventRemindController {

    @Autowired
    private EventRemindService eventRemindService;

    /**
     * 查询事件提醒
     * @param queryParam 查询参数
     * @return 事件提醒集合
     */
    @SaCheckLogin
    @PostMapping("/eventRemind/query")
    @ApiParam(name = "queryParam", value = "查询参数", required = true)
    public Response queryEventRemind(@RequestBody QueryParam queryParam){
        return eventRemindService.queryEventRemind(queryParam);
    }


}
