package cn.goroute.smart.post.model.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springdoc.api.annotations.ParameterObject;

/**
 * @Author: Alickx
 * @Date: 2022/09/25/18:13
 * @Description: 文章缩略信息对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper=true)
@Schema(name = "文章缩略DTO对象")
@ParameterObject
public class PostAbbreviationDTO extends PostBaseDTO {

	/**
	 * 文章摘要
	 */
	@Parameter(description = "文章摘要")
	private String summary;
}
