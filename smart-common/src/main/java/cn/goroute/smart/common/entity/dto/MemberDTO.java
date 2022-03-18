package cn.goroute.smart.common.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private String uid;

    /**
     * 呢称
     */
    private String nickName;
    /**
     * 性别 0 = 男 1= 女
     */
    private String gender;
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
     * QQ号码
     */
    private String qqNumber;
    /**
     * 0 = 正常 1 = 不可评论
     */
    private Integer commentStatus;
    /**
     * 用户的标签 0 = 普通用户
     */
    private Integer userTag;
    /**
     * 用户状态 0 = 正常
     */
    private Integer status;
}
