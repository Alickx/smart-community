package cn.goroute.smart.common.utils;


/**
 * 常量
 *
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

    public static final Integer POST_THUMB_TYPE = 1;

    public static final Integer DEFAULT_STATUS = 0;


    public static final String POST_ES_INDEX= "smart-post";


    /**
     * 菜单类型
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
