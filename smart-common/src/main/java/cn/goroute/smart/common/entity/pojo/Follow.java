package cn.goroute.smart.common.entity.pojo;

import cn.goroute.smart.common.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户关注表
 * @author Alickx
 * @TableName t_follow
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_follow")
@Data
public class Follow extends BaseEntity implements Serializable {

    /**
     * 用户uid
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long memberUid;

    /**
     * 关注目标的用户uid
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "关注目标的用户uid不能为空")
    private Long toMemberUid;

    /**
     * 关注状态
     */
    private Integer status;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}