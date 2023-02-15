package cn.goroute.smart.user.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 用户信息表
* @author Alickx
 * @TableName user_profile
*/
@Data
@TableName(value ="user_profile")
public class UserProfile implements Serializable {

    /**
    * 主键id
    */
    @NotNull(message="[主键id]不能为空")
    @TableId
    private Long id;

    /**
     * 用户id
     */
    @NotNull(message="[用户id]不能为空")
    private Long userId;
    /**
    * 呢称
    */
    @Size(max= 50,message="编码长度不能超过50")
    @Length(max= 50,message="编码长度不能超过50")
    private String nickName;
    /**
    * 登录账号
    */
    @NotBlank(message="[登录账号]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @Length(max= 255,message="编码长度不能超过255")
    private String email;
    /**
    * 性别 0 = 男 1= 女
    */
    @Size(max= 1,message="编码长度不能超过1")
    @Length(max= 1,message="编码长度不能超过1")
    private String gender;
    /**
    * 手机号
    */
    @Size(max= 20,message="编码长度不能超过20")
    @Length(max= 20,message="编码长度不能超过20")
    private String phone;
    /**
    * 头像地址
    */
    @Size(max= 500,message="编码长度不能超过500")
    @Length(max= 500,message="编码长度不能超过500")
    private String avatar;
    /**
    * 个人介绍
    */
    @Size(max= 255,message="编码长度不能超过255")
    @Length(max= 255,message="编码长度不能超过255")
    private String intro;
    /**
    * 粉丝数
    */
    @NotNull(message="[粉丝数]不能为空")
    private Integer fanNum;
    /**
    * 关注数
    */
    @NotNull(message="[关注数]不能为空")
    private Integer followNum;
	/**
	 * 等级
	 */
	@NotNull(message="[等级]不能为空")
	private Integer level;
    /**
    * 积分
    */
    @NotNull(message="[积分]不能为空")
    private Integer score;
	/**
	 * 发布文章数
	 */
	@NotNull(message="[发布文章数]不能为空")
	private Integer articleNum;
    /**
    * gitee地址
    */
    @Size(max= 255,message="编码长度不能超过255")
    @Length(max= 255,message="编码长度不能超过255")
    private String gitee;
    /**
    * github地址
    */
    @Size(max= 255,message="编码长度不能超过255")
    @Length(max= 255,message="编码长度不能超过255")
    private String github;
    /**
    * 访问系统
    */
    @Size(max= 255,message="编码长度不能超过255")
    @Length(max= 255,message="编码长度不能超过255")
    private String os;
    /**
    * QQ号码
    */
    @Size(max= 20,message="编码长度不能超过20")
    @Length(max= 20,message="编码长度不能超过20")
    private String qqNumber;
    /**
    * 访问浏览器
    */
    @Size(max= 255,message="编码长度不能超过255")
    @Length(max= 255,message="编码长度不能超过255")
    private String browser;
    /**
    * 用户的标签 0 = 普通用户
    */
    private Integer userTag;
    /**
    * 用户状态 0 = 正常
    */
    @NotNull(message="[用户状态 0 = 正常]不能为空")
    private Integer state;
    /**
    * 上一次登录的时间
    */
    private LocalDateTime lastLoginTime;
    /**
    * 上一次登录的ip
    */
    @Size(max= 50,message="编码长度不能超过50")
    @Length(max= 50,message="编码长度不能超过50")
    private String lastLoginIp;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    private LocalDateTime updateTime;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    private LocalDateTime createTime;
}
