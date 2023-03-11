package cn.goroute.smart.notice.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SourceTypeEnum {

	/**
	 * 事件源类型 - 文章
	 */
	POST(0, "文章"),


	/**
	 * 事件源类型 - 评论
	 */
	COMMENT(1, "评论"),

	/**
	 * 事件源类型 - 其他
	 */
	OTHER(2, "其他"),




	;

	private final Integer code;

	private final String desc;

}
