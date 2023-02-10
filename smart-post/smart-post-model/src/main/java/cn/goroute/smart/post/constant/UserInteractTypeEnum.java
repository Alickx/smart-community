package cn.goroute.smart.post.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserInteractTypeEnum {


	/**
	 * 文章
	 */
	POST(0, "文章"),
	/**
	 * 评论
	 */
	COMMENT(1, "评论"),
	/**
	 * 回复
	 */
	REPLY(2, "回复");

	private final Integer code;

	private final String desc;

}
