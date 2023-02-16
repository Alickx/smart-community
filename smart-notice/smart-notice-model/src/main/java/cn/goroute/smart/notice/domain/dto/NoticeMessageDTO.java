package cn.goroute.smart.notice.domain.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;

import java.time.LocalDateTime;

/**
 * @author: Alickx
 * @Date: 2023/02/16 11:56:41
 * @Description:
 */
@Data
@Schema(description = "站内通知DTO @author: Alickx")
@ParameterObject
public class NoticeMessageDTO {

    @Parameter(description = "主键")
    private Long id;

    @Parameter(description = "通知内容")
    private String content;

    @Parameter(description = "通知所在文章id")
    private Long postId;

    @Parameter(description = "通知所在文章标题")
    private String postTitle;

    @Parameter(description = "通知时间")
    private LocalDateTime createTime;

    @Parameter(description = "通知状态 0未读 1已读")
    private Integer status;

    @Parameter(description = "发送者id")
    private Long senderId;

    @Parameter(description = "通知来源类型,源类型 0 = 文章 1 = 评论")
    private Integer sourceType;

}
