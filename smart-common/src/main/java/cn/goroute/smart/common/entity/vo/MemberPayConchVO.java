package cn.goroute.smart.common.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: Alickx
 * @Date: 2022/03/20/13:16
 * @Description: 用户贝壳付款VO
 */
@Data
public class MemberPayConchVO implements Serializable {

    /**
     * 金额
     */
    private BigDecimal price;

    /**
     * 用户uid
     */
    private String memberUid;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品uid
     */
    private Integer productUid;

    /**
     * 赠送目标uid
     */
    private String toUid;

    /**
     * 交易类型 0=购买 1=赠送
     */
    private Integer type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
