package cn.goroute.smart.post.constant;


import cn.hutool.core.date.DateUtil;

/**
 * @author Alickx
 * @Description: 文章服务常量
 */
public class PostRedisConstant {


	public static class PostKey {

		// 文章阅读数缓存key
		public static String POST_VIEW_COUNT_KEY = "POST:READ:COUNT";

		// 24小时文章阅读数排行榜缓存key
		public static String POST_VIEW_COUNT_RANK_KEY = "POST:READ:COUNT:RANK";

		// 文章拓展信息key
		public static String POST_EXPAND_INFO_KEY = "POST:EXPAND:INFO";

		// 文章拓展信息待更新Set列表key
		public static String POST_EXPAND_INFO_UPDATE_LIST_KEY = "POST:EXPAND:INFO:UPDATE:LIST";

		public static String getTodayPostViewCountKey() {
			return POST_VIEW_COUNT_KEY + ":" + DateUtil.today();
		}

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
