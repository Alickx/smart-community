package cn.goroute.smart.post.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostStateEnum {

	NORMAL(0, "正常"),

	AUDIT(1, "审核中"),

	AUDIT_FAIL(2, "审核不通过"),


	;


	private final Integer code;

	private final String desc;
}
