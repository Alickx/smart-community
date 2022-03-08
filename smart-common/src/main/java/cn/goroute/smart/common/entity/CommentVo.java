package cn.goroute.smart.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {
    private String uid;
    /**
     * 文章uid
     */
    private String postUid;
    /**
     * 点赞某条评论的uid
     */
    private String toUid;
    /**
     * 回复某个人的uid或点赞评论的uid
     */
    private String toMemberUid;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论 = 0 点赞 = 1
     */
    private Integer type;
    /**
     * 一级评论uid
     */
    private String firstCommentUid;

}
