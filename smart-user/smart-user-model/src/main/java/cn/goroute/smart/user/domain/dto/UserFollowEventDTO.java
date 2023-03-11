package cn.goroute.smart.user.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: Alickx
 * @Date: 2023/03/11/9:59
 * @Description: 用户关注事件
 */
@Data
public class UserFollowEventDTO implements Serializable {

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 关注目标的用户id
	 */
	private Long toUserId;

	/**
	 * 关注时间
	 */
	private LocalDateTime followTime;

}
