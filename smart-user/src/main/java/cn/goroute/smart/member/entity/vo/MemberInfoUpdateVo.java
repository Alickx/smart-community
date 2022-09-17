package cn.goroute.smart.member.entity.vo;

import lombok.Data;

/**
 * @author Alickx
 */
@Data
public class MemberInfoUpdateVo {

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

}
