package cn.goroute.smart.post.model.qo;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/09/26/15:35
 * @Description: 文章查询对象
 */
@Schema(title = "文章查询对象")
@ParameterObject
@Data
public class PostQO {

	/**
	 * 板块id
	 */
	@Parameter(description = "板块Id")
	private Long categoryId;

	/**
	 * 文章作者id
	 */
	@Parameter(description = "文章作者id")
	private Long authorId;

}
