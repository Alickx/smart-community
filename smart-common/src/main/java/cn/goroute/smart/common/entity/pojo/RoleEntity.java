package cn.goroute.smart.common.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-28 18:52:38
 */
@Data
@TableName("t_role")
public class RoleEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键uid
     */
    @TableId(type = IdType.AUTO)
    private Integer uid;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色的描述
     */
    private String description;
    /**
     *
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;
    /**
     *
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;

}
