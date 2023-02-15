package cn.goroute.smart.notice.domain.vo;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;

import java.time.LocalDateTime;

/**
 * @author: Alickx
 * @Date: 2023/02/15 11:05:27
 * @Description:
 */
@Data
@Schema(description = "站内通知VO @author: Alickx")
@ParameterObject
public class NoticeMessageVO {

	@Parameter(description = "主键")
	private Long id;

	@Parameter(description = "通知标题")
	private String title;

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

}
