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
	public static class Thumb {

		public static final String THUMB_TOPIC = "thumb_topic";

		public static final String THUMB_HANDLE_GROUP = "thumb_handle_group";

		public static final String THUMB_HANDLE_TAG = "thumb_handle_tag";

	}

	/**
	 * 评论
	 */
	public static class Comment {

		public static final String COMMENT_TOPIC = "comment_topic";

		public static final String COMMENT_HANDLE_GROUP = "comment_handle_group";

		public static final String COMMENT_HANDLE_TAG = "comment_handle_tag";

	}

	public static class Post {

		public static final String POST_TOPIC = "post_topic";

		// 文章风控事件
		public static final String POST_RISK_HANDLE_GROUP = "post_risk_handle_group";

		public static final String POST_RISK_HANDLE_TAG = "post_risk_handle_tag";

		// 文章同步到ES
		public static final String POST_SYNC_SAVE_ES_HANDLE_GROUP = "post_sync_save_es_handle_group";

		public static final String POST_SYNC_SAVE_ES_HANDLE_TAG = "post_sync_save_es_handle_tag";

	}


}
