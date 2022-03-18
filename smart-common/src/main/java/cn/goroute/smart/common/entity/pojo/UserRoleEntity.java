package cn.goroute.smart.common.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户角色关联表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-28 18:52:38
 */
@Data
@TableName("t_user_role")
public class UserRoleEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer uid;
    /**
     * 用户的uid
     */
    private String userUid;
    /**
     * 角色的uid
     */
    private int roleUid;
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
