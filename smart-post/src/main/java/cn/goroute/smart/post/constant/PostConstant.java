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
		 * 评论点赞缓存key
		 */
		 public static final String COMMENT_THUMB_KEY = "comment:thumb:";

		/**
		 * 评论点赞缓存过期时间
		 */
		public static final long COMMENT_THUMB_EXPIRE = 60 * 60 * 24 * 3;


		/**
		 * 评论点赞缓存key
		 */
		public static final String REPLY_THUMB_KEY = "reply:thumb:";

		/**
		 * 评论点赞缓存过期时间
		 */
		public static final long REPLY_THUMB_EXPIRE = 60 * 60 * 24 * 3;



		/**
		 * 文章点赞缓存ttl字段
		 */
		public static final String THUMB_TTL_FIELD = "ttl";

		/**
		 * 文章点赞缓存最小文章id字段
		 */
		public static final String THUMB_MIN_CID_FIELD = "minCid";

	}
}
