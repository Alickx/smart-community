package cn.goroute.smart.post.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/09/27/14:05
 * @Description: 标签
 */
@Data
@Schema(name = "标签DTO")
@ParameterObject
public class TagDTO {

	/**
	 * 主键id
	 */
	private Long tagId;

	/**
	 * 标签内容
	 */
	private String content;

	/**
	 * 标签介绍
	 */
	private String intro;

}