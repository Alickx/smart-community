package cn.goroute.smart.post.constant;


/**
 * @author Alickx
 */
public class PostConstant {

    /**
     * 正常状态
     */
    public static final Integer NORMAL_STATUS = 0;

    /**
     * 删除状态
     */
    public static final Integer DELETE_STATUS = 1;

    /**
     * 审核状态
     */
    public static final Integer CHECK_STATUS = 2;

    /**
     * 不可见状态
     */
    public static final Integer INVISIBLE_STATUS = 3;

    /**
     * 公开
     */
    public static final String PUBLISH = "1";

    /**
     * 不公开
     */
    public static final String NOT_PUBLISH = "0";

    /**
     * 点赞评论类型
     */
    public static final int THUMB_COMMENT_TYPE = 0;

    /**
     * 点赞回复类型
     */
    public static final int THUMB_REPLY_TYPE = 1;

    /**
     * 点赞文章类型
     */
    public static final int THUMB_POST_TYPE = 2;

    /**
     * 事件提醒未读状态
     */
    public static final int REMIND_NOT_READ_STATE = 0;

    /**
     * 事件提醒已读状态
     */
    public static final int REMIND_HAVE_READ_STATE = 1;

    /**
     * 一级评论
     */
    public static final int COMMENT_TYPE = 0;

    /**
     * 评论中回复
     */
    public static final int REPLY_TYPE = 1;

    /**
     * 发布新文章
     */
    public static final String POST_SAVE_TYPE_NEW = "0";

    /**
     * 编辑文章
     */
    public static final String POST_SAVE_TYPE_EDIT = "1";


    /**
     *
     * 点赞状态
     *
     */

    /**
     * 点赞
     */
    public static final int THUMB_STATUS_NORMAL = 0;

    /**
     * 取消点赞
     */
    public static final int THUMB_STATUS_CANCEL = 1;
}
