package cn.goroute.smart.auth.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IdentityTypeEnum {

	/**
	 * 手机号
	 */
	PHONE(0, "手机号"),

	/**
	 * 邮箱
	 */
	EMAIL(1, "邮箱"),

	/**
	 * 用户名
	 */
	USERNAME(2, "用户名"),

	/**
	 * 微信
	 */
	WECHAT(3, "微信"),

	;

	private final Integer code;

	private final String desc;

}
