package cn.goroute.smart.post.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Alickx
 * @Date: 2023/01/07/11:34
 * @Description:
 */
@Data
public class ThumbDTO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 点赞用户id
     */
    private Long userId;

    /**
     * 点赞内容的id
     */
    private Long toId;

    /**
     * 点赞类型 0 = 评论 1 = 文章
     */
    private Integer type;

    /**
     * 逻辑删除 1 = 已取消点赞
     */
    private Integer deleted;

    /**
     * 点赞时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否是点赞 true = 点赞 false = 取消点赞
     */
    private Boolean isSave;

}
