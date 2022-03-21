package cn.goroute.smart.common.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Alickx
 * @Date: 2022/03/20/15:06
 * @Description: 商品购买VO
 */
@Data
public class ProductPayVO implements Serializable {

    private Integer productUid;

    private Integer type;

    private String toUid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
