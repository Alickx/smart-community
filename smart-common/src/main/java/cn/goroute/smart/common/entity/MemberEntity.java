package cn.goroute.smart.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户信息表
 * 
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:45:33
 */
@Data
@TableName("t_member")
public class MemberEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键uid
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private String uid;
	/**
	 * 呢称
	 */
	@TableField(fill = FieldFill.INSERT)
	private String nickName;
	/**
	 * 登录账号
	 */
	private String email;
	/**
	 * 密码
	 */
	private String passWord;
	/**
	 * 性别 0 = 男 1= 女
	 */
	private String gender;
	/**
	 * 手机号
	 */
	private Integer phone;
	/**
	 * 头像地址
	 */
	@TableField(fill = FieldFill.INSERT)
	private String avatar;
	/**
	 * 个人介绍
	 */
	private String intro;
	/**
	 * 粉丝数
	 */
	private Integer fans;
	/**
	 * 关注数
	 */
	private Integer follow;
	/**
	 * 积分
	 */
	private Integer score;
	/**
	 * 生日
	 */
	private Date birthday;
	/**
	 * gitee地址
	 */
	private String gitee;
	/**
	 * github地址
	 */
	private String github;
	/**
	 * 访问系统
	 */
	private String os;
	/**
	 * QQ号码
	 */
	private String qqNumber;
	/**
	 * 0 = 正常 1 = 不可评论
	 */
	private Integer commentStatus;
	/**
	 * 访问浏览器
	 */
	private String browser;
	/**
	 * 用户的标签 0 = 普通用户
	 */
	private Integer userTag;
	/**
	 * 用户状态 0 = 正常
	 */
	private Integer status;
	/**
	 * 上一次登录的时间
	 */
	private LocalDateTime lastLoginTime;
	/**
	 * 上一次登录的ip
	 */
	private String lastLoginIp;
	/**
	 * 
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updatedTime;
	/**
	 *
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createdTime;

}
