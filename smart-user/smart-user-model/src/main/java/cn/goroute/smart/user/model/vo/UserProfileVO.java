package cn.goroute.smart.user.model.vo;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import org.springdoc.api.annotations.ParameterObject;

/**
 * @Author: Alickx
 * @Date: 2023/01/30/22:52
 * @Description:
 */
@Data
@ToString
@Schema(title = "用户信息VO")
@ParameterObject
public class UserProfileVO {

	/**
	 * 呢称
	 */
	@Parameter(description = "呢称")
	private String nickName;

	/**
	 * 性别 0 = 男 1= 女
	 */
	@Parameter(description = "性别 0 = 男 1= 女")
	private String gender;
	/**
	 * 头像地址
	 */
	@Parameter(description = "头像地址")
	private String avatar;
	/**
	 * 个人介绍
	 */
	@Parameter(description = "个人介绍")
	private String intro;
	/**
	 * gitee地址
	 */
	@Parameter(description = "gitee地址")
	private String gitee;
	/**
	 * github地址
	 */
	@Parameter(description = "github地址")
	private String github;
	/**
	 * QQ号码
	 */
	@Parameter(description = "QQ号码")
	private String qqNumber;
}
