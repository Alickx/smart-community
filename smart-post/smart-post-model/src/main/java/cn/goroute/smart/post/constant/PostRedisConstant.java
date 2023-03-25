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

	}
}
