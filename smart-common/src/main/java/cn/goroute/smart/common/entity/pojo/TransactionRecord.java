package cn.goroute.smart.common.entity.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户交易记录表
 * @TableName t_transaction_record
 */
@TableName(value ="t_transaction_record")
@Data
public class TransactionRecord implements Serializable {
    /**
     * 流水号
     */
    @TableId
    private String uid;

    /**
     * 用户uid
     */
    private String memberUid;

    /**
     * 花费金额
     */
    private BigDecimal cost;

    /**
     * 用户呢称
     */
    private String memberNickName;

    /**
     * 交易前的余额
     */
    private BigDecimal memberConch;

    /**
     * 用户交易后的余额
     */
    private BigDecimal memberTransactionConch;

    /**
     * 商品uid
     */
    private Integer productUid;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 赠送目标uid
     */
    private String toUid;

    /**
     * 交易类型 0=购买 1=赠送
     */
    private Integer type;

    /**
     * 交易时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}