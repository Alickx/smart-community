package cn.goroute.smart.common.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品订单表
 * @TableName t_product_order
 */
@TableName(value ="t_product_order")
@Data
public class ProductOrderEntity implements Serializable {
    /**
     * 
     */
    @TableId
    private String uid;

    /**
     * 
     */
    private String memberUid;

    /**
     * 
     */
    private String productUid;

    /**
     * 类型 0 =自我购买 1= 赠送
     */
    private Integer type;

    /**
     * 赠送目标的uid
     */
    private String toUid;

    /**
     * 交易号
     */
    private String transactionUid;

    /**
     * 创建订单时，商品的金额
     */
    private BigDecimal price;

    /**
     * 订单状态 0 = 已创建 1 = 已付款 ，2 = 订单已过期
     */
    private Integer status;

    /**
     * 订单创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 付款时间
     */
    private LocalDateTime paymentTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}