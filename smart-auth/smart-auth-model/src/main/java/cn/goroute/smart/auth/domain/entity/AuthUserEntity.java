package cn.goroute.smart.auth.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 用户授权表
* @TableName auth_user
*/
@Data
@TableName("auth_user")
public class AuthUserEntity implements Serializable {

    /**
    * 主键id
    */
    private Long id;

	/**
	 * 用户用户名
	 */
	private String userName;

	/**
	 * 用户密码
	 */
	private String password;

	/**
	 * 用户邮箱
	 */
	private String userEmail;

	/**
	 * 0 = 未激活 1 = 已激活
	 */
	private Integer isActivate;

    /**
    * 绑定时间
    */
    private LocalDateTime createTime;
    /**
    * 更新绑定时间
    */
    private LocalDateTime updateTime;
}
