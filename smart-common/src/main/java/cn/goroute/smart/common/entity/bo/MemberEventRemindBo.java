package cn.goroute.smart.common.entity.bo;

import lombok.Data;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/08/29/14:21
 * @Description: 用户业务对象
 */
@Data
public class MemberEventRemindBo {


    private int uid;
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
