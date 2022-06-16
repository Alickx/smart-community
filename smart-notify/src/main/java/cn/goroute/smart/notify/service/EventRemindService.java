package cn.goroute.smart.notify.service;

import cn.goroute.smart.common.entity.pojo.EventRemind;
import cn.goroute.smart.common.utils.QueryParam;
import cn.goroute.smart.common.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Alickx
 * @description 针对表【t_event_remind(事件提醒表)】的数据库操作Service
 * @createDate 2022-03-26 08:45:27
 */
public interface EventRemindService extends IService<EventRemind> {

    Result queryEventRemind(QueryParam queryParam);

    Result queryUnreadCountRemind();
}
