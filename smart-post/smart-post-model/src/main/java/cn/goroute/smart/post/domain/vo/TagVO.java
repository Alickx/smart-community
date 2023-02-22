package cn.goroute.smart.post.domain.vo;

import lombok.Data;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/09/27/14:05
 * @Description: 标签
 */
@Data
public class TagVO {

	/**
	 * 主键id
	 */
	private Long tagId;

	/**
	 * 标签内容
	 */
	private String content;

	/**
	 * 标签介绍
	 */
	private String intro;

}
