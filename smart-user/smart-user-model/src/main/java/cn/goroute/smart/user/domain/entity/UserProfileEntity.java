package cn.goroute.smart.user.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户信息表
 *
 * @author Alickx
 * @TableName user_profile
 */
@Data
@TableName(value = "user_profile")
public class UserProfileEntity implements Serializable {

    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 呢称
     */
    private String nickName;

    /**
     * 登录账号
     */
    private String email;

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
    private Integer fanNum;

    /**
     * 关注数
     */
    private Integer followNum;

    /**
     * 发布文章数
     */
    private Integer articleNum;

    /**
     * 访问系统
     */
    private String os;

    /**
     * 访问浏览器
     */
    private String browser;

    /**
     * 用户状态 0 = 正常 1 = 禁用
     */
    private Integer state;

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
    private LocalDateTime updateTime;

    /**
     *
     */
    private LocalDateTime createTime;
}
