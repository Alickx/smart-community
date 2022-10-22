package cn.goroute.smart.post.constant;


/**
 * @author Alickx
 * @Description: 文章服务常量
 */
public class PostConstant {

	/**
	 * 点赞业务常量
	 */
    public static class Thumb {
		/**
		 * 文章点赞缓存key
		 */
		public static final String POST_THUMB_KEY = "post:thumb:";

		/**
		 * 文章点赞缓存过期时间
		 */
		public static final long POST_THUMB_EXPIRE = 60 * 60 * 24 * 7;

		/**
		 * 文章点赞缓存ttl字段
		 */
		public static final String POST_THUMB_TTL = "ttl";

		/**
		 * 文章点赞缓存最小文章id字段
		 */
		public static final String POST_THUMB_MIN_CID = "minCid";
	}
}
