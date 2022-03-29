package cn.goroute.smart.common.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-28 18:52:38
 */
@Data
@TableName("t_permission")
public class Permission implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer uid;
    /**
     * 权限名称
     */
    private String permissionName;

    private String url;
    /**
     *
     */
    private String description;
    /**
     *
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;
    /**
     *
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;

}
