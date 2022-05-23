package cn.goroute.smart.common.entity.pojo;

import cn.goroute.smart.common.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户角色关联表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-28 18:52:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_user_role")
public class UserRole extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户的uid
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userUid;
    /**
     * 角色的uid
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long roleUid;

}
