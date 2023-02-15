package cn.goroute.smart.notice.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MsgTypeEnum {

	/**
	 * 点赞
	 */
	THUMB(0, "点赞"),

	/**
	 * 评论
	 */
	COMMENT(1, "评论"),

	/**
	 * 关注
	 */
	FOLLOW(2, "关注"),

	/**
	 * 文章内@操作
	 */
	AT(3, "@"),

	/**
	 * 系统提醒
	 */
	SYSTEM(4, "系统提醒");

	private final Integer code;

	private final String desc;

}
