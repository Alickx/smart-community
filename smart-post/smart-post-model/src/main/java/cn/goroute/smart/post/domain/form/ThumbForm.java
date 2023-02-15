package cn.goroute.smart.post.domain.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: Alickx
 * @Date: 2023/02/14 16:28:01
 * @Description:
 */
@Data
public class ThumbForm {

	/**
	 * 对象id
	 */
	@NotNull
	private Long toId;

	/**
	 * 点赞类型
	 * @link {cn.goroute.smart.post.domain.enums.ThumbTypeEnum}
	 */
	@NotNull
	private Integer type;


}
