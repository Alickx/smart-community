package cn.goroute.smart.post.entity.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/09/27/14:22
 * @Description: 文章扩展对象
 */
@Data
@Schema(name = "文章扩展对象")
@ParameterObject
public class PostExpansionBO {

	/**
	 * 是否收藏
	 */
	@Parameter(description = "是否评论")
	@JsonProperty(defaultValue = "false")
	private Boolean isComment;

	/**
	 * 是否点赞
	 */
	@Parameter(description = "是否点赞")
	@JsonProperty(defaultValue = "false")
	private Boolean isThumb;

}
