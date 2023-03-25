package cn.goroute.smart.user.domain.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Alickx
 * @Date: 2023/03/25/16:01
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileQueryForm {

	private Long userId;

	private String nickName;

}
