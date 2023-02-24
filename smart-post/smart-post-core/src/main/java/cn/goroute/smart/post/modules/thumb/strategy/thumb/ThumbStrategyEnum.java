package cn.goroute.smart.post.modules.thumb.strategy.thumb;

import cn.goroute.smart.common.util.SpringUtil;
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
	POST(0, "文章", "postThumbStrategy"),


	COMMENT(1, "评论", "commentThumbStrategy"),

	REPLY(2, "回复", "replyThumbStrategy")

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
				return SpringUtil.getBean(value.getBeanName(), ThumbStrategy.class);
			}
		}
		return null;
	}

}
