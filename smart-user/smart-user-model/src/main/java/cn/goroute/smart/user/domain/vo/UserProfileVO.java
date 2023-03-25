package cn.goroute.smart.user.domain.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: Alickx
 * @Date: 2023/01/30/22:52
 * @Description:
 */
@Data
@ToString
public class UserProfileVO {

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 呢称
	 */
	private String nickName;

	/**
	 * 头像地址
	 */
	private String avatar;

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
	 * 个人介绍
	 */
	private String intro;

	/**
	 * 用户状态 0 = 正常
	 */
	private Integer status;
}
