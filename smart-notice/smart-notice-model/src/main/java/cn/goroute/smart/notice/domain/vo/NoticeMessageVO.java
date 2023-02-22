package cn.goroute.smart.notice.domain.vo;

import cn.goroute.smart.user.domain.vo.UserProfileVO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: Alickx
 * @Date: 2023/02/15 11:05:27
 * @Description:
 */
@Data
public class NoticeMessageVO {

	private Long id;

	private String title;

	private String content;

	private Long postId;

	private String postTitle;

	private LocalDateTime createTime;

	private Integer status;

	private UserProfileVO sender;

	private Integer sourceType;

}
