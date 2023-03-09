package cn.goroute.smart.user.domain.form;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @Author: Alickx
 * @Date: 2023/03/05/10:07
 * @Description: 用户关注请求
 */
@Data
@Builder
public class UserSaveFollowForm {

	/**
	 * 关注的用户id
	 */
	@NotNull(message = "关注的用户id不能为空")
	private Long toUserId;

	/**
	 * 关注时间
	 */
	@NotNull(message = "关注时间不能为空")
	private LocalDateTime followTime;

	/**
	 * 关注类型 1:关注 2:取消关注
	 */
	@NotNull
	private Integer type;

}
