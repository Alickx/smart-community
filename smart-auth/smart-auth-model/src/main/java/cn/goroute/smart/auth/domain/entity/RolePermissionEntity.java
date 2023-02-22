package cn.goroute.smart.auth.domain.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 
* @TableName role_permission
*/
@Data
public class RolePermissionEntity implements Serializable {

	/**
	 *
	 */
	private Long id;
	/**
	 * 角色id
	 */
	private Long roleId;
	/**
	 * 权限id
	 */
	private Long permissionUid;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;

}
