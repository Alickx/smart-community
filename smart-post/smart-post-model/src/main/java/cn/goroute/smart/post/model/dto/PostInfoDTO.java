package cn.goroute.smart.post.model.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springdoc.api.annotations.ParameterObject;

/**
 * @Author: Alickx
 * @Date: 2022/09/25/18:12
 * @Description: 文章信息对象
 */
@Data
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper = true)
@Schema(name = "文章详情DTO对象")
@ParameterObject
public class PostInfoDTO extends PostBaseDTO {

	/**
	 * 文章内容
	 */
	@Parameter(description = "文章内容")
	private String content;


	/**
	 * 文章摘要
	 */
	@Parameter(description = "文章摘要")
	private String summary;

}
