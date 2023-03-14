package cn.goroute.smart.post.constant;


/**
 * @author Alickx
 * @Description: 文章服务常量
 */
public class PostRedisConstant {


	public static class PostKey {

		// 文章阅读缓存key set集合 存储用户id
		public static final String POST_READ_KEY = "POST:READ:";

		// 文章拓展信息key
		public static String EXPAND_INFO_KEY = "EXPAND:INFO:";

		// 文章拓展信息待更新Set列表key
		public static String POST_EXPAND_INFO_UPDATE_LIST_KEY = "EXPAND:INFO:POST:UPDATE:LIST";

		// 评论拓展信息待更新Set列表key
		public static String COMMENT_EXPAND_INFO_UPDATE_LIST_KEY = "EXPAND:INFO:COMMENT:UPDATE:LIST";

		// 回复拓展信息待更新Set列表key
		public static String REPLY_EXPAND_INFO_UPDATE_LIST_KEY = "EXPAND:INFO:REPLY:UPDATE:LIST";

	}
}
