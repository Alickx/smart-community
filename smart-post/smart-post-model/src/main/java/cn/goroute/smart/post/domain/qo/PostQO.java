package cn.goroute.smart.post.domain.qo;

import lombok.Data;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/09/26/15:35
 * @Description: 文章查询对象
 */
@Data
public class PostQO {

	/**
	 * 板块名称
	 */
	private String categoryName;

	/**
	 * 标签名称
	 */
	private String tagName;

	/**
	 * 文章作者id
	 */
	private Long userId;

}
