package cn.goroute.smart.post.domain.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: Alickx
 * @Date: 2022/09/25/18:19
 * @Description: 板块对象
 */
@Data
@ToString
public class CategoryDTO {

	/**
	 * 板块id
	 */
	private Long categoryId;

	/**
	 * 板块名称
	 */
	private String name;

	/**
	 * 板块图标
	 */
	private String icon;

	/**
	 * 板块链接
	 */
	private String url;

	/**
	 * 板块介绍
	 */
	private String intro;

}
