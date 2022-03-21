package cn.goroute.smart.common.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 商品组表
 * @TableName t_product_group
 */
@TableName(value ="t_product_group")
@Data
public class ProductGroupEntity implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer uid;

    /**
     * 组名
     */
    private String groupName;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序字段
     */
    private Integer sort;

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