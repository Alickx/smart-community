package cn.goroute.smart.common.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 事件提醒表
 * @author Alickx
 * @TableName t_event_remind
 */
@TableName(value ="t_event_remind")
@Data
public class EventRemind implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
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
     * 接受通知的用户uid
     */
    private String recipientUid;

    /**
     * 提醒时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 读取的时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}