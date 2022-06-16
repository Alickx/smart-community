package cn.goroute.smart.member.manage.impl;

import cn.goroute.smart.common.constant.NotificationConstant;
import cn.goroute.smart.common.entity.pojo.EventRemind;
import cn.goroute.smart.common.entity.pojo.MemberBan;
import cn.goroute.smart.member.manage.IMemberBanManage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Alickx
 * @Date: 2022/06/16/15:04
 * @Description:
 */
@Service
public class MemberBanManageImpl implements IMemberBanManage {

    private static final String EXCHANGE = "exchange.direct";

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 发送消息通知
     *
     * @param memberBan
     */
    @Override
    public void sendNotify(MemberBan memberBan,String description) {
        EventRemind eventRemind = new EventRemind();
        eventRemind.setActionType(NotificationConstant.REMIND_SYSTEM_ACTION_TYPE);
        eventRemind.setSourceUid(null);
        eventRemind.setSourceType(null);
        eventRemind.setSourceContent(description);
        eventRemind.setSourceTitle(null);
        eventRemind.setSenderUid(memberBan.getBanHandlerId());
        eventRemind.setRecipientUid(Long.valueOf(memberBan.getBanUserId()));
        rabbitTemplate.convertAndSend(EXCHANGE, "smart.event.remind", eventRemind);
    }
}
