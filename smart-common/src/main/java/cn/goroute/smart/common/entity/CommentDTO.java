package cn.goroute.smart.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private String uid;

    /**
     * 用户uid
     */
    private MemberDTO fromMember;

    /**
     * 回复某个人的uid
     */
    private MemberDTO toMember;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞数量
     */
    private Integer thumbCount;

    /**
     * 子级评论
     */
    private List<CommentDTO> replyInfo;

    /**
     * 是否点赞
     */
    private Boolean isLike;

    /**
     *
     */
    private LocalDateTime createdTime;


}
