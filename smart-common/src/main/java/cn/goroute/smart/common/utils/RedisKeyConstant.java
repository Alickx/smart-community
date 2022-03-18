package cn.goroute.smart.common.utils;

public class RedisKeyConstant {

    public static final String PERMISSION_LIST_KEY = "auth:permission:";

    public static final String ROLE_LIST_KEY = "auth:role:";

    public static final String REG_SEND_BAN_KEY = "reg:SendBan:";

    public static final String REG_CAPTCHA_KEY = "reg:Captcha:";

    public static final String REG_SEND_COUNT_KEY = "reg:SendCount:";

    public static final String REG_SEND_SLEEP_KEY = "reg:SendSleep:";

    public static final String POST_THUMB_KEY = "post:thumb";

    public static final String POST_CACHE_KEY = "post:cache:";

    public static final String POST_COLLECT_KEY = "post:collect";

    public static final String USER_INFO_KEY="user:info:";

    public static final String POST_COUNT_KEY="post:count:";

    public static final String POST_THUMB_COUNT_KEY = "thumbCount";

    public static final String POST_COMMENT_COUNT_KEY = "commentCount";

    public static String getThumbOrCollectKey(String memberUid, String toUid) {
        StringBuilder builder = new StringBuilder();
        builder.append(memberUid);
        builder.append("::");
        builder.append(toUid);
        return builder.toString();
    }
}
