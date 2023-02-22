package cn.goroute.smart.notice.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: Alickx
 * @Date: 2023/02/16 11:56:41
 * @Description:
 */
@Data
public class NoticeMessageDTO {

    private Long id;

    private String content;

    private Long postId;

    private String postTitle;

    private LocalDateTime createTime;

    private Integer status;

    private Long senderId;

    private Integer sourceType;

}
