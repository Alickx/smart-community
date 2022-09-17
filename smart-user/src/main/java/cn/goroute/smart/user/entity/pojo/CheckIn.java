package cn.goroute.smart.user.entity.pojo;

import cn.goroute.smart.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 签到表
 * @author Alickx
 * @TableName t_check_in
 */
@TableName(value ="t_check_in")
@EqualsAndHashCode(callSuper = true)
@Data
public class CheckIn extends BaseEntity implements Serializable  {
    /**
     * 签到人uid
     */
    private Long memberId;

    /**
     * 签到日期
     */
    private LocalDateTime checkInDate;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}