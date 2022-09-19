package cn.goroute.smart.common.constant;

import com.hccake.ballcat.common.model.result.ResultCode;
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

    USERNAME_OR_PASSWORD_ERROR(10001, "用户名或密码错误"),
    USER_NOT_EXIST(10002, "用户不存在"),
    USER_ALREADY_EXIST(10003, "用户已存在"),
    LOGIN_EXPIRED(10004, "登录已过期"),
    USER_NOT_LOGIN(10005, "用户未登录"),

    // 通用错误
    PARAM_ERROR(20001, "参数错误"),
    UNKNOWN_ERROR(20002, "未知错误"),
    SYSTEM_ERROR(20003, "系统错误"),
    ;

    private int code;

    private String message;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
