package cn.goroute.smart.common.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @Author: Alickx
 * @Date: 2022/03/24/15:08
 * @Description:
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventRemindDto {

    /**
     * 点赞人信息
     */
    private MemberDto sender;

    /**
     * 主键id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long uid;

    /**
     * 操作类型
     */
    private Integer actionType;

    /**
     * 事件源id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long sourceUid;

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
     * 是否已读
     */
    private Integer state;

    /**
     * 操作者uid
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long senderUid;

    /**
     * 提醒时间
     */
    private LocalDateTime createdTime;

}
