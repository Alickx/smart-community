package cn.goroute.smart.user.entity.pojo;

import cn.goroute.smart.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户信息表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:45:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_member")
public class Member extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 呢称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickName;
    /**
     * 登录账号
     */
    @NotBlank(message = "登录账号不能为空")
    private String email;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String passWord;
    /**
     * 性别 0 = 男 1= 女 2=私密
     */
    private String gender;
    /**
     * 手机号
     */
    private Integer phone;
    /**
     * 头像地址
     */
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

}
