package cn.goroute.smart.common.entity.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @Author: Alickx
 * @Date: 2022/08/30/1:54
 * @Description: 提醒事件Bo
 */
@Data
public class EventRemindBo{

    /**
     * 操作类型
     */
    private Integer actionType;

    /**
     * 事件源id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long sourceId;

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
    private Long senderId;

    /**
     * 接受通知的用户uid
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long recipientId;

    private static final long serialVersionUID = 1L;

}
