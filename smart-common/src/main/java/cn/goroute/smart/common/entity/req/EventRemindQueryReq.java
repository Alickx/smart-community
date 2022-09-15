package cn.goroute.smart.common.entity.req;

import cn.goroute.smart.base.entity.BasePage;
import lombok.Data;

/**
 * @Author: Alickx
 * @Date: 2022/09/04/16:34
 * @Description: 事件通知请求类
 */
@Data
public class EventRemindReq extends BasePage {

    private String actionType;

}
