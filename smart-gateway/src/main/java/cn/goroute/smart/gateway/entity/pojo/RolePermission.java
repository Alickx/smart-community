package cn.goroute.smart.gateway.entity.pojo;

import cn.goroute.smart.common.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 角色权限关联表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-28 18:52:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_role_permission")
public class RolePermission extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色uid
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long roleUid;
    /**
     * 权限uid
     */
    private String permissionUid;
}
