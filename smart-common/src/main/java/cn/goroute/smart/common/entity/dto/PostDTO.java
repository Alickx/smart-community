package cn.goroute.smart.common.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Alickx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    /**
     * 文章主键
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long uid;

    /**
     * 文章题目
     */
    private String title;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 0 = 不公布  1 = 公布
     */
    private String isPublish;
    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 文章创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 文章收藏次数
     */
    private Integer collectCount;
    /**
     * 文章点赞次数
     */
    private Integer thumbCount;
    /**
     * 评论次数
     */
    private Integer commentCount;
    /**
     * 作者信息
     */
    private MemberDTO authorInfo;

    /**
     * 文章点赞信息
     */
    private Boolean isLike;

    /**
     * 文章收藏信息
     */
    private Boolean isCollect;

    /**
     * 文章状态
     */
    private Integer status;

}
