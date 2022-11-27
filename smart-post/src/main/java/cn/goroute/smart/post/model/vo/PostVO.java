package cn.goroute.smart.post.model.vo;

import lombok.Data;
import lombok.ToString;

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
	@NotNull(message = "板块id不能为空")
	private Long categoryId;

	/**
	 * 标签id
	 */
	@NotNull(message = "标签id不能为空")
	private Long tagId;

	/**
	 * 文章题目
	 */
	@NotNull(message = "文章题目不能为空")
	private String title;

	/**
	 * 文章内容
	 */
	@NotNull(message = "文章内容不能为空")
	private String content;

	/**
	 * 0:false = 不公布  1:true = 公布
	 */
	@NotNull(message = "公布状态不能为空")
	private Boolean isPublish;

}
