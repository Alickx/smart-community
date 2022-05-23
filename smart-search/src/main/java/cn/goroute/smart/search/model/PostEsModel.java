package cn.goroute.smart.search.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostEsModel {
    /**
     * 文章uid
     */
    private Long uid;
    /**
     * 文章题目
     */
    private String title;
    /**
     * 文章作者Uid
     */
    private Long memberUid;
    /**
     * 文章摘要
     */
    private String summary;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 文章创建时间
     */
    private LocalDateTime createdTime;
    /**
     *文章点赞总数
     */
    private Integer thumbCount;
    /**
     * 文章评论总数
     */
    private Integer commentCount;
    /**
     * 文章状态
     */
    private Integer status;
}
