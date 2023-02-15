package cn.goroute.smart.post.domain.vo;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/09/27/10:22
 * @Description: 文章
 */
@Data
@ToString
public class PostVO {

	/**
	 * 板块id
	 */
	@NotBlank(message = "板块不能为空")
	private String categoryName;

	/**
	 * 标签id
	 */
	@NotBlank(message = "标签不能为空")
	private String tagName;

	/**
	 * 文章题目
	 */
	@NotBlank(message = "文章题目不能为空")
	private String title;

	/**
	 * 文章内容
	 */
	@NotBlank(message = "文章内容不能为空")
	private String content;

	/**
	 * 0:false = 不公布  1:true = 公布
	 */
	@NotNull(message = "公布状态不能为空")
	private Boolean isPublish;

}
