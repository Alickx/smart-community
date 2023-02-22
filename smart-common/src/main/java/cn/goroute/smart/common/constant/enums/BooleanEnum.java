package cn.goroute.smart.common.constant.enums;

import lombok.AllArgsConstructor;

/**
 * Boolean 类型常量
 */
@AllArgsConstructor
public enum BooleanEnum {

	/**
	 * 是
	 */
	TRUE(true, 1),
	/**
	 * 否
	 */
	FALSE(false, 0);

	private final Boolean booleanValue;

	private final Integer intValue;

	public Boolean booleanValue() {
		return booleanValue;
	}

	public Integer intValue() {
		return intValue;
	}

}
