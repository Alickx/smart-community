package cn.goroute.smart.gateway.entity.pojo;

import cn.goroute.smart.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 权限表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-28 18:52:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_permission")
public class Permission extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限路径
     */
    private String url;
    /**
     *
     */
    private String description;

}
