package cn.goroute.smart.common.entity.dto;

import cn.goroute.smart.common.entity.pojo.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long uid;
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

    public MemberDTO(Member member) {
        this.uid = member.getUid();
        this.nickName = member.getNickName();
        this.gender = member.getGender();
        this.commentStatus = member.getCommentStatus();
        this.avatar = member.getAvatar();
        this.fans = member.getFans();
        this.follow = member.getFollow();
        this.status = member.getStatus();
        this.gitee = member.getGitee();
        this.github = member.getGithub();
        this.intro = member.getIntro();
        this.qqNumber = member.getQqNumber();
        this.score = member.getScore();
        this.userTag = member.getUserTag();
    }

}
