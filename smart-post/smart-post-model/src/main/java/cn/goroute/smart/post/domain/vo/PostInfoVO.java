package cn.goroute.smart.post.domain.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author: Alickx
 * @Date: 2022/09/25/18:12
 * @Description: 文章信息对象
 */
@Data
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper = true)
public class PostInfoVO extends PostBaseVO {

	/**
	 * 文章内容
	 */
	private String content;


	/**
	 * 文章摘要
	 */
	private String summary;

}
