package cn.goroute.smart.post.strategy.thumb;

import cn.goroute.smart.common.util.BeanUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/10/22/16:48
 * @Description: 点赞策略枚举
 */
@Getter
@AllArgsConstructor
public enum ThumbStrategyEnum {

	/**
	 * 文章点赞
	 */
	POST(1, "文章", "postThumbStrategy"),


	COMMENT(2, "评论", "commentThumbStrategy"),

	;

	private final Integer type;

	private final String desc;

	private final String beanName;

	/**
	 * 根据类型获取点赞策略
	 */
	public static ThumbStrategy getStrategyByType(Integer type) {
		for (ThumbStrategyEnum value : ThumbStrategyEnum.values()) {
			if (value.getType().equals(type)) {
				return (ThumbStrategy) BeanUtil.popBean(value.getBeanName());
			}
		}
		return null;
	}

}
