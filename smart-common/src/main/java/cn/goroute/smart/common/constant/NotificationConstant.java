package cn.goroute.smart.common.constant;

/**
 * @Author: Alickx
 * @Date: 2022/03/26/9:29
 * @Description:
 */
public class NotificationConstant {

    // -------------------操作类型-------------------

    /**
     * 点赞操作
     */
    public static final int REMIND_THUMB_ACTION_TYPE = 0;

    /**
     * 评论操作
     */
    public static final int REMIND_COMMENT_ACTION_TYPE = 1;

    /**
     * 关注操作
     */
    public static final int REMIND_FOLLOW_ACTION_TYPE = 2;

    /**
     * 文章内@操作
     */
    public static final int REMIND_AT_ACTION_TYPE = 3;

    /**
     * 系统提醒
     */
    public static final int REMIND_SYSTEM_ACTION_TYPE = 4;

    /**
     * 操作类型数组
     */
    public static final int[] REMIND_ARRAY = {
            REMIND_THUMB_ACTION_TYPE,
            REMIND_COMMENT_ACTION_TYPE,
            REMIND_FOLLOW_ACTION_TYPE,
            REMIND_AT_ACTION_TYPE ,
            REMIND_SYSTEM_ACTION_TYPE
    };


    // -------------------事件源类型-------------------

    /**
     * 事件源类型 - 文章
     */
    public static final int REMIND_SOURCE_POST_TYPE = 0;

    /**
     * 事件源类型 - 评论
     */
    public static final int REMIND_SOURCE_COMMENT_TYPE = 1;

    /**
     * 事件源类型 - 回复
     */
    public static final int REMIND_SOURCE_REPLY_TYPE = 2;

    /**
     * 事件源类型 - 用户
     */
    public static final int REMIND_SOURCE_MEMBER_TYPE = 3;


    // -------------------已读状态-------------------

    public static final int STATE_UNREAD = 0;

    public static final int STATE_HAVE_READ = 1;
}
