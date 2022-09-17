package cn.goroute.smart.user.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 用户信息表
* @TableName user_profile
*/
public class UserProfile implements Serializable {

    /**
    * 主键id
    */
    @NotNull(message="[主键id]不能为空")
    @ApiModelProperty("主键id")
    private Long id;
    /**
    * 呢称
    */
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("呢称")
    @Length(max= 50,message="编码长度不能超过50")
    private String nickName;
    /**
    * 登录账号
    */
    @NotBlank(message="[登录账号]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("登录账号")
    @Length(max= 255,message="编码长度不能超过255")
    private String email;
    /**
    * 性别 0 = 男 1= 女
    */
    @Size(max= 1,message="编码长度不能超过1")
    @ApiModelProperty("性别 0 = 男 1= 女")
    @Length(max= 1,message="编码长度不能超过1")
    private String gender;
    /**
    * 手机号
    */
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("手机号")
    @Length(max= 20,message="编码长度不能超过20")
    private String phone;
    /**
    * 头像地址
    */
    @Size(max= 500,message="编码长度不能超过500")
    @ApiModelProperty("头像地址")
    @Length(max= 500,message="编码长度不能超过500")
    private String avatar;
    /**
    * 个人介绍
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("个人介绍")
    @Length(max= 255,message="编码长度不能超过255")
    private String intro;
    /**
    * 粉丝数
    */
    @NotNull(message="[粉丝数]不能为空")
    @ApiModelProperty("粉丝数")
    private Integer fans;
    /**
    * 关注数
    */
    @NotNull(message="[关注数]不能为空")
    @ApiModelProperty("关注数")
    private Integer follow;
    /**
    * 积分
    */
    @NotNull(message="[积分]不能为空")
    @ApiModelProperty("积分")
    private Integer score;
    /**
    * gitee地址
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("gitee地址")
    @Length(max= 255,message="编码长度不能超过255")
    private String gitee;
    /**
    * github地址
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("github地址")
    @Length(max= 255,message="编码长度不能超过255")
    private String github;
    /**
    * 访问系统
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("访问系统")
    @Length(max= 255,message="编码长度不能超过255")
    private String os;
    /**
    * QQ号码
    */
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("QQ号码")
    @Length(max= 20,message="编码长度不能超过20")
    private String qqNumber;
    /**
    * 0 = 正常 1 = 不可评论
    */
    @NotNull(message="[0 = 正常 1 = 不可评论]不能为空")
    @ApiModelProperty("0 = 正常 1 = 不可评论")
    private Integer commentState;
    /**
    * 访问浏览器
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("访问浏览器")
    @Length(max= 255,message="编码长度不能超过255")
    private String browser;
    /**
    * 用户的标签 0 = 普通用户
    */
    @ApiModelProperty("用户的标签 0 = 普通用户")
    private Integer userTag;
    /**
    * 用户状态 0 = 正常
    */
    @NotNull(message="[用户状态 0 = 正常]不能为空")
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
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("上一次登录的ip")
    @Length(max= 50,message="编码长度不能超过50")
    private String lastLoginIp;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private LocalDateTime updateTime;
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private LocalDateTime createTime;

    /**
    * 主键id
    */
    private void setId(Long id){
    this.id = id;
    }

    /**
    * 呢称
    */
    private void setNickName(String nickName){
    this.nickName = nickName;
    }

    /**
    * 登录账号
    */
    private void setEmail(String email){
    this.email = email;
    }

    /**
    * 性别 0 = 男 1= 女
    */
    private void setGender(String gender){
    this.gender = gender;
    }

    /**
    * 手机号
    */
    private void setPhone(String phone){
    this.phone = phone;
    }

    /**
    * 头像地址
    */
    private void setAvatar(String avatar){
    this.avatar = avatar;
    }

    /**
    * 个人介绍
    */
    private void setIntro(String intro){
    this.intro = intro;
    }

    /**
    * 粉丝数
    */
    private void setFans(Integer fans){
    this.fans = fans;
    }

    /**
    * 关注数
    */
    private void setFollow(Integer follow){
    this.follow = follow;
    }

    /**
    * 积分
    */
    private void setScore(Integer score){
    this.score = score;
    }

    /**
    * gitee地址
    */
    private void setGitee(String gitee){
    this.gitee = gitee;
    }

    /**
    * github地址
    */
    private void setGithub(String github){
    this.github = github;
    }

    /**
    * 访问系统
    */
    private void setOs(String os){
    this.os = os;
    }

    /**
    * QQ号码
    */
    private void setQqNumber(String qqNumber){
    this.qqNumber = qqNumber;
    }

    /**
    * 0 = 正常 1 = 不可评论
    */
    private void setCommentState(Integer commentState){
    this.commentState = commentState;
    }

    /**
    * 访问浏览器
    */
    private void setBrowser(String browser){
    this.browser = browser;
    }

    /**
    * 用户的标签 0 = 普通用户
    */
    private void setUserTag(Integer userTag){
    this.userTag = userTag;
    }

    /**
    * 用户状态 0 = 正常
    */
    private void setState(Integer state){
    this.state = state;
    }

    /**
    * 上一次登录的时间
    */
    private void setLastLoginTime(LocalDateTime lastLoginTime){
    this.lastLoginTime = lastLoginTime;
    }

    /**
    * 上一次登录的ip
    */
    private void setLastLoginIp(String lastLoginIp){
    this.lastLoginIp = lastLoginIp;
    }

    /**
    * 
    */
    private void setUpdateTime(LocalDateTime updateTime){
    this.updateTime = updateTime;
    }

    /**
    * 
    */
    private void setCreateTime(LocalDateTime createTime){
    this.createTime = createTime;
    }


    /**
    * 主键id
    */
    private Long getId(){
    return this.id;
    }

    /**
    * 呢称
    */
    private String getNickName(){
    return this.nickName;
    }

    /**
    * 登录账号
    */
    private String getEmail(){
    return this.email;
    }

    /**
    * 性别 0 = 男 1= 女
    */
    private String getGender(){
    return this.gender;
    }

    /**
    * 手机号
    */
    private String getPhone(){
    return this.phone;
    }

    /**
    * 头像地址
    */
    private String getAvatar(){
    return this.avatar;
    }

    /**
    * 个人介绍
    */
    private String getIntro(){
    return this.intro;
    }

    /**
    * 粉丝数
    */
    private Integer getFans(){
    return this.fans;
    }

    /**
    * 关注数
    */
    private Integer getFollow(){
    return this.follow;
    }

    /**
    * 积分
    */
    private Integer getScore(){
    return this.score;
    }

    /**
    * gitee地址
    */
    private String getGitee(){
    return this.gitee;
    }

    /**
    * github地址
    */
    private String getGithub(){
    return this.github;
    }

    /**
    * 访问系统
    */
    private String getOs(){
    return this.os;
    }

    /**
    * QQ号码
    */
    private String getQqNumber(){
    return this.qqNumber;
    }

    /**
    * 0 = 正常 1 = 不可评论
    */
    private Integer getCommentState(){
    return this.commentState;
    }

    /**
    * 访问浏览器
    */
    private String getBrowser(){
    return this.browser;
    }

    /**
    * 用户的标签 0 = 普通用户
    */
    private Integer getUserTag(){
    return this.userTag;
    }

    /**
    * 用户状态 0 = 正常
    */
    private Integer getState(){
    return this.state;
    }

    /**
    * 上一次登录的时间
    */
    private LocalDateTime getLastLoginTime(){
    return this.lastLoginTime;
    }

    /**
    * 上一次登录的ip
    */
    private String getLastLoginIp(){
    return this.lastLoginIp;
    }

    /**
    * 
    */
    private LocalDateTime getUpdateTime(){
    return this.updateTime;
    }

    /**
    * 
    */
    private LocalDateTime getCreateTime(){
    return this.createTime;
    }

}
