package cn.goroute.smart.post.domain.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author: Alickx
 * @Date: 2022/09/25/18:13
 * @Description: 文章缩略信息对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper=true)
public class PostAbbreviationVO extends PostBaseVO {

	/**
	 * 文章摘要
	 */
	private String summary;
}
