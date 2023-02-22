package cn.goroute.smart.common.constant.enums;

import cn.goroute.smart.common.modules.result.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author: Alickx
 * @Date: 2022/09/18/1:42
 * @Description: 返回结果枚举
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCodeEnum implements ResultCode {

	/**
	 * =========================用户服务错误=========================
	 */
	USER_COMMON_ERROR(10000, "用户服务错误"),
    USERNAME_OR_PASSWORD_ERROR(10001, "用户名或密码错误"),
    LOGIN_EXPIRED(10004, "登录已过期"),
    USER_NOT_LOGIN(10005, "用户未登录"),


	/**
	 * =========================文章服务错误=========================
	 */

	POST_NOT_EXIST(20001, "文章不存在"),

	POST_NOT_PUBLISH(20002, "文章为私密文章"),

	POST_HAS_RISK(20003, "文章存在风险"),



	// 通用错误
    PARAM_ERROR(1100, "参数错误"),
    UNKNOWN_ERROR(1101, "未知错误"),
    SYSTEM_ERROR(1102, "系统错误"),
	NO_PERMISSION(1103, "无权限"),
    ;

    private int code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
