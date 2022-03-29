package cn.goroute.smart.common.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户关注表
 * @TableName t_follow
 */
@TableName(value ="t_follow")
@Data
public class Follow implements Serializable {
    /**
     * 主键uid
     */
    @TableId(type = IdType.AUTO)
    private Integer uid;

    /**
     * 用户uid
     */
    private String memberUid;

    /**
     * 关注目标的用户uid
     */
    private String toMemberUid;

    /**
     * 关注状态
     */
    private Integer status;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}