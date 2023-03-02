package cn.goroute.smart.common.constant;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/10/22/16:04
 * @Description: 消息队列业务常量
 */
public class RocketMqBizConstant {

    /**
     * 点赞
     */
    public static class ThumbMqConstant {

        public static final String THUMB_TOPIC = "thumb_topic";

        public static final String THUMB_NOTICE_GROUP = "thumb_notice_group";

    }

    /**
     * 评论
     */
    public static class CommentMqConstant {

        public static final String COMMENT_TOPIC = "comment_topic";

        // 通知消费组
        public static final String COMMENT_NOTICE_GROUP = "comment_notice_group";

    }

    /**
     * 文章
     */
    public static class PostMqConstant {

        public static final String POST_TOPIC = "post_topic";

        // 文章同步到ES
        public static final String POST_SYNC_SAVE_ES_HANDLE_GROUP = "post_sync_save_es_handle_group";

		// 文章发布事件
		public static final String POST_PUBLISH_EVENT_GROUP = "post_publish_event_group";
    }

    /**
     * 认证
     */
    public static class AuthMqConstant {

        public static final String AUTH_TOPIC = "auth_topic";

        // 用户注册
        public static final String USER_REGISTER_GROUP = "user_register_group";

    }


}
