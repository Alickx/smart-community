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
		public static String POST_EXPAND_INFO_KEY = "POST:EXPAND:INFO";

		// 文章拓展信息待更新Set列表key
		public static String POST_EXPAND_INFO_UPDATE_LIST_KEY = "POST:EXPAND:INFO:UPDATE:LIST";
	}


	public static class TagKey {

		// 文章标签缓存key
		public static final String POST_TAG_KEY = "POST:TAG";

	}

	public static class CategoryKey {

		// 文章分类缓存key
		public static final String POST_CATEGORY_KEY = "POST:CATEGORY";
	}
}
