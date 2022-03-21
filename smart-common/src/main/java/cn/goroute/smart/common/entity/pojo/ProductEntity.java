package cn.goroute.smart.common.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 社区商品表
 * @TableName t_product
 */
@TableName(value ="t_product")
@Data
public class ProductEntity implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
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
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}