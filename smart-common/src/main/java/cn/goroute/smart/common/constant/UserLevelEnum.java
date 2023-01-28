package cn.goroute.smart.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum UserLevelEnum {

	/**
	 * level 1
	 */
	LEVEL_1(1, 100),

	/**
	 * level 2
	 */
	LEVEL_2(2, 300),

	/**
	 * level 3
	 */
	LEVEL_3(3, 500),


	/**
	 * level 4
	 */
	LEVEL_4(4, 1000),

	/**
	 * level 5
	 */
	LEVEL_5(5, 2000),

	/**
	 * level 6
	 */
	LEVEL_6(6, 3000),

	;

	/**
	 * 等级
	 */
	private final Integer level;

	/**
	 * 积分
	 */
	private final Integer score;

}
