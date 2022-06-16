package cn.goroute.smart.common.constant;

/**
 * @Author: Alickx
 * @Date: 2022/06/09/16:12
 * @Description:
 */
public enum BanTypeEnum {

    /**
     * 封禁ip
     */
    BAN_IP(1, "封禁IP"),

    /**
     * 封禁用户（账号一切操作禁止）
     */
    BAN_USER(2, "封禁用户"),

    /**
     * 封禁发帖
     */
    BAN_POST(3, "封禁发帖"),

    /**
     * 封禁评论
     */
    BAN_COMMENT_REPLY(4, "封禁评论和回复"),

    /**
     * 封禁站内私信
     */
    BAN_MESSAGE(5, "封禁私信");

    private Integer code;

    private String desc;

    BanTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
