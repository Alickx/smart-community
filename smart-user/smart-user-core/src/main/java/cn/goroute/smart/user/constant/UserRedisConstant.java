package cn.goroute.smart.user.constant;

/**
 * @Author: Alickx
 * @Date: 2023/01/30/23:15
 * @Description:
 */
public class UserRedisConstant {
	/**
	 * 用户信息缓存key
	 */
	public final static String USER_PROFILE = "USER:PROFILE";

	/**
	 * 用户关注缓存key
	 * 以zset的形式存储
	 * key: USER:FOLLOW:userId
	 * value: toUserId
	 * score: 关注时间
	 */
	public final static String USER_FOLLOW = "USER:FOLLOW";

}
