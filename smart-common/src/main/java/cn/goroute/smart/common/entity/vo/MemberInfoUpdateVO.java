package cn.goroute.smart.common.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class MemberInfoUpdateVO {

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
     * 生日
     */
    private Date birthday;

}
