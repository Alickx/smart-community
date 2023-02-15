package cn.goroute.smart.post.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 板块表
 * @TableName category
 */
@TableName(value ="category")
@Data
public class CategoryEntity implements Serializable {
    /**
     * 板块id
     */
	@TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 板块名称
     */
    private String name;

    /**
     * 板块图标
     */
    private String icon;

    /**
     * 板块链接
     */
    private String url;

    /**
     * 板块介绍
     */
    private String intro;

    /**
     * 板块状态 0 = 正常 1 = 关闭
     */
    private Integer state;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}