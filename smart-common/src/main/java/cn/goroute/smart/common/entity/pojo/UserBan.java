package cn.goroute.smart.common.entity.pojo;

import cn.goroute.smart.common.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 封禁表
 * @author Alickx
 * @TableName t_user_ban
 */
@TableName(value ="t_user_ban")
@Data
public class UserBan extends BaseEntity implements Serializable {

    /**
     * 封禁用户id
     */
    private String banUserId;

    /**
     * 封禁类型
     */
    private Integer banType;

    /**
     * 封禁操作者Id
     */
    private String banHandlerId;

    /**
     * 封禁理由
     */
    private String banReason;

    /**
     * 封禁开始时间
     */
    private LocalDateTime banTime;

    /**
     * 封禁结束时间
     */
    private LocalDateTime banEndTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}