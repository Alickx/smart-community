package cn.goroute.smart.common.constant;


/**
 * 常量
 * @author Alickx
 */
public class Constant {
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 升序
     */
    public static final String ASC = "asc";

    /**
     * url前缀
     */
    public static final char[] URL_PREFIX = {'h', 't', 't', 'p', ':', '/', '/'};


    /**
     *
     **
     * @Description: 用户封禁类型
     * @return:
     * @Author: Alicx
     * @Date: 2022/5/23
     */

    /**
     * 禁止评论
     */
    public static final String MUTE_USER = "1";

    /**
     * 禁止发帖
     */
    public static final String BAN_POST = "2";

    /**
     * 冻结账号
     */
    public static final String FREEZE_USER = "3";

    public static String getBanType(String type) {
        switch (type) {
            case "1":
                return "禁止评论";
            case "2":
                return "禁止发帖";
            case "3":
                return "冻结账号";
            default:
                return "其他";
        }
    }


    /**
     *
     * 封禁管理，搜索类型
     *
     */

    /**
     * 用户名
     */
    public static final String BY_USER_NAME = "1";

    /**
     * 用户ID
     */
    public static final String BY_USER_ID = "2";


    /**
     *
     * @Description: 用户状态
     *
     */

    /**
     * 正常
     */
    public static final String DEFAULT_USER_STATUS = "0";

    /**
     * 封禁（任一封禁都算进入封禁状态）
     */
    public static final String BAD_USER_STATUS = "1";

}
