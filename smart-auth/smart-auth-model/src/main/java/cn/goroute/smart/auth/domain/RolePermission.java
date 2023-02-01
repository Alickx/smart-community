package cn.goroute.smart.auth.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 
* @TableName role_permission
*/
@Data
public class RolePermission implements Serializable {

	/**
	 *
	 */
	@NotNull(message = "[]不能为空")
	private Long id;
	/**
	 * 角色id
	 */
	@NotNull(message = "[角色id]不能为空")
	private Long roleId;
	/**
	 * 权限id
	 */
	@NotNull(message = "[权限id]不能为空")
	private Long permissionUid;
	/**
	 * 创建时间
	 */
	@NotNull(message = "[创建时间]不能为空")
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	@NotNull(message = "[更新时间]不能为空")
	private LocalDateTime updateTime;

}
