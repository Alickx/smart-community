package cn.goroute.smart.common.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author caiguopeng
 */
@Data
public class UserProfileDto {

    /**
     * 主键id
     */
    @ApiModelProperty("用户id")
    private Long userId;
    /**
     * 呢称
     */
    @ApiModelProperty("呢称")
    private String nickName;

    /**
     * 性别 0 = 男 1= 女
     */
    @ApiModelProperty("性别 0 = 男 1= 女")
    private String gender;
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
     * 用户的标签 0 = 普通用户
     */
    @ApiModelProperty("用户的标签 0 = 普通用户")
    private Integer userTag;
    /**
     * 用户状态 0 = 正常
     */
    @ApiModelProperty("用户状态 0 = 正常")
    private Integer state;

}
