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
	POST(0, "文章", "POST"),
	/**
	 * 评论
	 */
	COMMENT(1, "评论", "COMMENT"),
	/**
	 * 回复
	 */
	REPLY(2, "回复", "REPLY"),



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
