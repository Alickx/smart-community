package cn.goroute.smart.member.manage;

import cn.goroute.smart.common.entity.pojo.MemberBan;

/**
 * @Author: Alickx
 * @Date: 2022/06/16/15:04
 * @Description:
 */
public interface IMemberBanManage {
    /**
     * 发送封禁消息通知
     */
    void sendNotify(MemberBan memberBan,String description);

}
