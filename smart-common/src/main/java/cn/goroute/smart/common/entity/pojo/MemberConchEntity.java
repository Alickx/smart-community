package cn.goroute.smart.common.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户贝壳余额
 * @TableName t_member_conch
 */
@TableName(value ="t_member_conch")
@Data
public class MemberConchEntity implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer uid;

    /**
     * 用户主键uid
     */
    private String memberUid;

    /**
     * 用户贝壳余额
     */
    private BigDecimal conch;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;

    /**
     * 
     */
    private LocalDateTime createdTime;

    /**
     * 
     */
    private LocalDateTime updatedTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}