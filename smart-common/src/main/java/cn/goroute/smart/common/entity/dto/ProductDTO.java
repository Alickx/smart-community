package cn.goroute.smart.common.entity.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: Alickx
 * @Date: 2022/03/19/15:46
 * @Description: 商品类VO
 */
@Data
public class ProductDTO {

    /**
     * 主键
     */
    private Integer uid;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品介绍
     */
    private String description;

    /**
     * 金额
     */
    private BigDecimal price;

    /**
     * 商品图片
     */
    private String img;

    /**
     * 商品组id
     */
    private Integer groupUid;

    /**
     * 商品库存
     */
    private Integer stock;
}
