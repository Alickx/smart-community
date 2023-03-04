package cn.goroute.smart.post.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文章拓展信息
 * @TableName t_post_expand_info
 */
@TableName(value ="t_post_expand_info")
@Data
@Builder
public class PostExpandInfoEntity implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 文章id
     */
    private Long postId;

    /**
     * 点赞量
     */
    private Integer thumbCount;

    /**
     * 评论量
     */
    private Integer commentCount;

    /**
     * 查看量
     */
    private Integer viewCount;

    /**
     * 收藏量
     */
    private Integer collectCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}