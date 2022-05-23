package cn.goroute.smart.common.constant;

public class RedisKeyConstant {

    /**
     * 用户权限
     */
    public static final String PERMISSION_LIST_KEY = "auth:permission:";

    /**
     * 用户角色
     */
    public static final String ROLE_LIST_KEY = "auth:role:";

    /**
     * 注册ban
     */
    public static final String REG_SEND_BAN_KEY = "reg:SendBan:";

    /**
     * 注册验证码
     */
    public static final String REG_CAPTCHA_KEY = "reg:Captcha:";

    /**
     * 发送次数
     */
    public static final String REG_SEND_COUNT_KEY = "reg:SendCount:";

    /**
     * 发送间隔
     */
    public static final String REG_SEND_SLEEP_KEY = "reg:SendSleep:";

    /**
     * 文章点赞信息
     */
    public static final String POST_THUMB_KEY = "post:thumb";

    /**
     * 文章缓存
     */
    public static final String POST_CACHE_KEY = "post:cache:";

    /**
     * 文章收藏信息
     */
    public static final String POST_COLLECT_KEY = "post:collect";

    /**
     * 文章点赞数，评论数，收藏数数量
     */
    public static final String POST_COUNT_KEY="post:count:";

    /**
     * 点赞数量
     */
    public static final String POST_THUMB_COUNT_KEY = "thumbCount";

    /**
     * 评论数量
     */
    public static final String POST_COMMENT_COUNT_KEY = "commentCount";

    /**
     * 收藏数量
     */
    public static final String POST_COLLECT_COUNT_KEY = "collectCount";




    public static String getThumbKey(Long memberUid, Long toUid) {
        return memberUid +
                ":" +
                toUid;
    }
}
