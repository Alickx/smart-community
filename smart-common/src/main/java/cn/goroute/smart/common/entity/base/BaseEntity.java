package cn.goroute.smart.common.entity.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Alickx
 * @Date: 2022/04/07/8:44
 * @Description:
 */
@Data
public class BaseEntity {

    /**
     * 主键id
     */
    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long uid;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;

    /**
     * 逻辑删除标识 0 = 未删除 1 = 已删除
     */
    @TableLogic
    private Integer deleted;

}
