package cn.goroute.smart.common.entity.pojo;

import cn.goroute.smart.common.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 事件提醒表
 * @author Alickx
 * @TableName t_event_remind
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_event_remind")
@Data
public class EventRemind extends BaseEntity implements Serializable {

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
    @NotNull(message = "操作者uid不能为空")
    private Long senderUid;

    /**
     * 接受通知的用户uid
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "接受通知的用户uid不能为空")
    private Long recipientUid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}