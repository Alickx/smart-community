package cn.goroute.smart.common.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Alickx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long uid;

    /**
     * 发送者
     */
    private MemberDTO fromMember;

    /**
     * 接受者
     */
    private MemberDTO toMember;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 0 = 一级评论 1 = 评论中回复
     */
    private Integer type;

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
    /**
     * 是否有更多
     */
    private Boolean hasMore;


}
