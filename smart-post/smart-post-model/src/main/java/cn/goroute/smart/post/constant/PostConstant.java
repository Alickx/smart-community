package cn.goroute.smart.post.constant;


import cn.hutool.core.date.DateUtil;

/**
 * @author Alickx
 * @Description: 文章服务常量
 */
public class PostConstant {


	public static class Post {

		// 文章阅读数缓存key
		public static String POST_VIEW_COUNT_KEY = "POST:READ:COUNT";

		// 24小时文章阅读数排行榜缓存key
		public static String POST_VIEW_COUNT_RANK_KEY = "POST:READ:COUNT:RANK";

		public static String getTodayPostViewCountKey() {
			return POST_VIEW_COUNT_KEY + ":" + DateUtil.today();
		}

	}


	public static class Tag {

		// 文章标签缓存key
		public static String POST_TAG_KEY = "POST:TAG";

	}

	public static class category {

		// 文章分类缓存key
		public static String POST_CATEGORY_KEY = "POST:CATEGORY";
	}
}
