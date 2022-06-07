package cn.goroute.smart.common.entity.dto;

import cn.goroute.smart.common.entity.pojo.Tag;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/06/06/19:24
 * @Description:
 */
@ToString
@Data
public class CategoryTagDTO {

    /**
     * 主键
     */
    private Long uid;

    /**
     * 板块名称
     */
    private String name;
    /**
     * 板块图标
     */
    private String icon;
    /**
     * 板块介绍
     */
    private String intro;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    /**
     * 逻辑删除标识 0 = 未删除 1 = 已删除
     */
    private Integer deleted;

    /**
     * 标签列表
     */
    private List<Tag> tags;

}
