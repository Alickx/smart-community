package cn.goroute.smart.common.constant;

/**
 * @Author: Alickx
 * @Date: 2022/06/17/15:16
 * @Description:
 */
public enum LimitType {

    /**
     * 默认全局限流
     */
    DEFAULT,

    /**
     * 根据ip进行限流
     */
    IP,

    /**
     * 根据用户进行限流
     */
    MEMBER;
}
