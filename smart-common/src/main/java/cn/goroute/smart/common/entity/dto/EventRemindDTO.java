package cn.goroute.smart.common.entity.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @Author: Alickx
 * @Date: 2022/03/24/15:08
 * @Description:
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRemindDTO {

    /**
     * 点赞人信息
     */
    private MemberDTO sender;

    /**
     * 主键id
     */
    private Integer uid;

    /**
     * 操作类型
     */
    private Integer actionType;

    /**
     * 事件源id
     */
    private String sourceUid;

    /**
     * 事件源类型
     */
    private Integer sourceType;

    /**
     * 事件源内容
     */
    private String sourceContent;

    /**
     * 事件源标题
     */
    private String sourceTitle;

    /**
     * 事件发生的地址
     */
    private String url;

    /**
     * 是否已读
     */
    private Integer state;

    /**
     * 操作者uid
     */
    private String senderUid;

    /**
     * 提醒时间
     */
    private LocalDateTime createdTime;

}
