package cn.goroute.smart.post.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文章元素类型枚举
 */
@Getter
@AllArgsConstructor
public enum PostItemTypeEnum {


	/**
	 * 文章
	 */
	POST(0, "文章", "post"),
	/**
	 * 评论
	 */
	COMMENT(1, "评论", "comment"),
	/**
	 * 回复
	 */
	REPLY(2, "回复", "reply"),



	;

	private final Integer code;

	private final String desc;

	private final String name;

	public static String getNameByCode(Integer code) {
		for (PostItemTypeEnum value : PostItemTypeEnum.values()) {
			if (value.getCode().equals(code)) {
				return value.getName();
			}
		}
		return null;
	}


}
