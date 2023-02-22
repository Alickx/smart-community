package cn.goroute.smart.user.domain.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author caiguopeng
 */
@Data
@ToString
public class UserProfileDTO {

	/**
	 * 主键id
	 */
	private Long userId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 呢称
	 */
	private String nickName;

	/**
	 * 头像地址
	 */
	private String avatar;

	/**
	 * 个人介绍
	 */
	private String intro;

	/**
	 * 粉丝数
	 */
	private Integer fanNum;

	/**
	 * 关注数
	 */
	private Integer followNum;

	/**
	 * 发布文章数
	 */
	private Integer articleNum;

	/**
	 * 浏览器
	 */
	private String browser;
	/**
	 * 用户状态 0 = 正常
	 */
	private Integer state;

}
