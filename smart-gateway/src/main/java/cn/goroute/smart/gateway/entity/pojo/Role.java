package cn.goroute.smart.gateway.entity.pojo;

import cn.goroute.smart.common.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-28 18:52:38
 */
@Data
@TableName("t_role")
public class Role extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色的描述
     */
    private String description;

}
