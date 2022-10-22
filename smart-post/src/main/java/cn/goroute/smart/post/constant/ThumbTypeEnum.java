package cn.goroute.smart.post.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/10/22/16:47
 * @Description: 点赞类型枚举
 */
@Getter
@AllArgsConstructor
public enum ThumbTypeEnum {

	/**
	 * 文章点赞
	 */
	POST(1, "文章点赞"),
	/**
	 * 评论点赞
	 */
	COMMENT(2, "评论点赞"),
	/**
	 * 回复点赞
	 */
	REPLY(3, "回复点赞");

	private final Integer code;

	private final String desc;

}
