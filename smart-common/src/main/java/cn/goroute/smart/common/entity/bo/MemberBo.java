package cn.goroute.smart.common.entity.bo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Alickx
 * @Date: 2022/09/03/16:30
 * @Description: 用户bo
 */
@Data
public class MemberBo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 呢称
     */
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
