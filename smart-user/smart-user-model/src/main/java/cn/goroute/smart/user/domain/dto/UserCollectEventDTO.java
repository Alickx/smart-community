package cn.goroute.smart.user.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Alickx
 * @Date: 2023/03/12/16:04
 * @Description: 用户收藏事件
 */
@Data
public class UserCollectEventDTO implements Serializable {

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 文章id
	 */
	private Long postId;

	/**
	 * 收藏状态
	 * 0：取消收藏 1：收藏
	 */
	private Integer collectStatus;

}
