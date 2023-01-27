package cn.goroute.smart.common.model.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import org.springdoc.api.annotations.ParameterObject;

/**
 * @author caiguopeng
 */
@Data
@ToString
@Schema(title = "用户信息")
@ParameterObject
public class UserProfileDTO {

	/**
	 * 主键id
	 */
	@Parameter(description = "用户id")
	private Long userId;
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
	 * 粉丝数
	 */
	@Parameter(description = "粉丝数")
	private Integer fans;
	/**
	 * 关注数
	 */
	@Parameter(description = "关注数")
	private Integer follow;
	/**
	 * 积分
	 */
	@Parameter(description = "积分")
	private Integer score;
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
	/**
	 * 0 = 正常 1 = 不可评论
	 */
	@Parameter(description = "0 = 正常 1 = 不可评论")
	private Integer commentState;
	/**
	 * 用户的标签 0 = 普通用户
	 */
	@Parameter(description = "用户的标签 0 = 普通用户")
	private Integer userTag;
	/**
	 * 用户状态 0 = 正常
	 */
	@Parameter(description = "用户状态 0 = 正常")
	private Integer state;

}
