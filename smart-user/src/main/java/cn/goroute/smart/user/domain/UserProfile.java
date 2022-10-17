package cn.goroute.smart.user.domain;

import io.swagger.annotations.ApiModelProperty;
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
public class UserProfile implements Serializable {

    /**
    * 主键id
    */
    @NotNull(message="[主键id]不能为空")
    @ApiModelProperty("主键id")
    private Long id;

    /**
     * 用户id
     */
    @NotNull(message="[用户id]不能为空")
    @ApiModelProperty("用户id")
    private Long userId;
    /**
    * 呢称
    */
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("呢称")
    @Length(max= 50,message="编码长度不能超过50")
    private String nickName;
    /**
    * 登录账号
    */
    @NotBlank(message="[登录账号]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("登录账号")
    @Length(max= 255,message="编码长度不能超过255")
    private String email;
    /**
    * 性别 0 = 男 1= 女
    */
    @Size(max= 1,message="编码长度不能超过1")
    @ApiModelProperty("性别 0 = 男 1= 女")
    @Length(max= 1,message="编码长度不能超过1")
    private String gender;
    /**
    * 手机号
    */
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("手机号")
    @Length(max= 20,message="编码长度不能超过20")
    private String phone;
    /**
    * 头像地址
    */
    @Size(max= 500,message="编码长度不能超过500")
    @ApiModelProperty("头像地址")
    @Length(max= 500,message="编码长度不能超过500")
    private String avatar;
    /**
    * 个人介绍
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("个人介绍")
    @Length(max= 255,message="编码长度不能超过255")
    private String intro;
    /**
    * 粉丝数
    */
    @NotNull(message="[粉丝数]不能为空")
    @ApiModelProperty("粉丝数")
    private Integer fans;
    /**
    * 关注数
    */
    @NotNull(message="[关注数]不能为空")
    @ApiModelProperty("关注数")
    private Integer follow;
    /**
    * 积分
    */
    @NotNull(message="[积分]不能为空")
    @ApiModelProperty("积分")
    private Integer score;
    /**
    * gitee地址
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("gitee地址")
    @Length(max= 255,message="编码长度不能超过255")
    private String gitee;
    /**
    * github地址
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("github地址")
    @Length(max= 255,message="编码长度不能超过255")
    private String github;
    /**
    * 访问系统
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("访问系统")
    @Length(max= 255,message="编码长度不能超过255")
    private String os;
    /**
    * QQ号码
    */
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("QQ号码")
    @Length(max= 20,message="编码长度不能超过20")
    private String qqNumber;
    /**
    * 0 = 正常 1 = 不可评论
    */
    @NotNull(message="[0 = 正常 1 = 不可评论]不能为空")
    @ApiModelProperty("0 = 正常 1 = 不可评论")
    private Integer commentState;
    /**
    * 访问浏览器
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("访问浏览器")
    @Length(max= 255,message="编码长度不能超过255")
    private String browser;
    /**
    * 用户的标签 0 = 普通用户
    */
    @ApiModelProperty("用户的标签 0 = 普通用户")
    private Integer userTag;
    /**
    * 用户状态 0 = 正常
    */
    @NotNull(message="[用户状态 0 = 正常]不能为空")
    @ApiModelProperty("用户状态 0 = 正常")
    private Integer state;
    /**
    * 上一次登录的时间
    */
    @ApiModelProperty("上一次登录的时间")
    private LocalDateTime lastLoginTime;
    /**
    * 上一次登录的ip
    */
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("上一次登录的ip")
    @Length(max= 50,message="编码长度不能超过50")
    private String lastLoginIp;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private LocalDateTime updateTime;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private LocalDateTime createTime;
}