package cn.goroute.smart.post.entity.dto;

import cn.goroute.smart.common.entity.dto.UserProfileDto;
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
public class CommentDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 发送者
     */
    private UserProfileDto fromMember;

    /**
     * 接受者
     */
    private UserProfileDto toMember;

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
    private Long thumbCount;

    /**
     * 子级评论
     */
    private List<CommentDto> replyInfo;

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
