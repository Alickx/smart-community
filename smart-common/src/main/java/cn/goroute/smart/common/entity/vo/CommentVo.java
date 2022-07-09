package cn.goroute.smart.common.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alickx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {

    private Long uid;
    /**
     * 文章uid
     */
    private Long postUid;
    /**
     * 回复某个人的uid或点赞评论的uid
     */
    private Long toMemberUid;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 一级评论uid
     */
    private Long firstCommentUid;

    /**
     * 0 = 一级评论 1 = 评论中回复
     */
    private Integer type;

}
