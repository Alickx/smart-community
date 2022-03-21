package cn.goroute.smart.common.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 商品库存表
 * @TableName t_product_stock
 */
@TableName(value ="t_product_stock")
@Data
public class ProductStock implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer uid;

    /**
     * 商品uid
     */
    private Integer productUid;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 乐观锁
     */
    private Integer version;

    /**
     * 
     */
    private LocalDateTime createdTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}