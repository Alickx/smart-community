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

		// 文章点赞数缓存key
		public static String POST_THUMB_COUNT_KEY = "POST:THUMB:COUNT";

		// 文章评论数缓存key
		public static String POST_COMMENT_COUNT_KEY = "POST:COMMENT:COUNT";

		// 文章收藏数缓存key
		public static String POST_COLLECT_COUNT_KEY = "POST:COLLECT:COUNT";


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
