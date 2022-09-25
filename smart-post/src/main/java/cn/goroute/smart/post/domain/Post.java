package cn.goroute.smart.post.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文章表
 * @author Alickx
 * @TableName post
 */
@TableName(value ="post")
@Data
public class Post implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 板块id
     */
    private Long categoryId;

    /**
     * 作者id
     */
    private Long authorId;

    /**
     * 文章题目
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章状态 0 = 正常
     */
    private Integer state;

    /**
     * 收藏数量
     */
    private Integer collectCount;

    /**
     * 点赞数量
     */
    private Integer thumbCount;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 0 = 不公布  1 = 公布
     */
    private Integer isPublish;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 逻辑删除 0 = 未删除 1 = 已删除
     */
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}