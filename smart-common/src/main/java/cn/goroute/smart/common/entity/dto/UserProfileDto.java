package cn.goroute.smart.common.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author caiguopeng
 */
@Data
public class UserProfileDto {

    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    private Long id;
    /**
     * 呢称
     */
    @ApiModelProperty("呢称")
    private String nickName;
    /**
     * 登录账号
     */
    @ApiModelProperty("登录账号")
    private String email;
    /**
     * 性别 0 = 男 1= 女
     */
    @ApiModelProperty("性别 0 = 男 1= 女")
    private String gender;
    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phone;
    /**
     * 头像地址
     */
    @ApiModelProperty("头像地址")
    private String avatar;
    /**
     * 个人介绍
     */
    @ApiModelProperty("个人介绍")
    private String intro;
    /**
     * 粉丝数
     */
    @ApiModelProperty("粉丝数")
    private Integer fans;
    /**
     * 关注数
     */
    @ApiModelProperty("关注数")
    private Integer follow;
    /**
     * 积分
     */
    @ApiModelProperty("积分")
    private Integer score;
    /**
     * gitee地址
     */
    @ApiModelProperty("gitee地址")
    private String gitee;
    /**
     * github地址
     */
    @ApiModelProperty("github地址")
    private String github;
    /**
     * 访问系统
     */
    @ApiModelProperty("访问系统")
    private String os;
    /**
     * QQ号码
     */
    @ApiModelProperty("QQ号码")
    private String qqNumber;
    /**
     * 0 = 正常 1 = 不可评论
     */
    @ApiModelProperty("0 = 正常 1 = 不可评论")
    private Integer commentState;
    /**
     * 访问浏览器
     */
    @ApiModelProperty("访问浏览器")
    private String browser;
    /**
     * 用户的标签 0 = 普通用户
     */
    @ApiModelProperty("用户的标签 0 = 普通用户")
    private Integer userTag;
    /**
     * 用户状态 0 = 正常
     */
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
    @ApiModelProperty("上一次登录的ip")
    private String lastLoginIp;
    /**
     *
     */
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
    /**
     *
     */
    @ApiModelProperty("粗昂见时间")
    private LocalDateTime createTime;

}
