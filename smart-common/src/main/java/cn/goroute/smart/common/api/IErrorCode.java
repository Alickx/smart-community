package cn.goroute.smart.common.api;

/**
 * @Author: Alickx
 * @Date: 2022/04/01/16:07
 * @Description:
 */
public interface IErrorCode {

    /**
     * 返回码
     */
    long getCode();

    /**
     * 返回信息
     */
    String getMessage();

}
