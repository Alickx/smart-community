package cn.goroute.smart.common.enums;


import lombok.Getter;

/**
 * @Author: Alickx
 * @Date: 2022/09/03/15:55
 * @Description: 返回结果状态码枚举类
 */
public enum ResponseCodeEnum {

    /**
     * 成功
     */
    SUCCESS(200, true),

    /**
     * 失败
     */
    FAILURE(500, false),

    /**
     * 错误
     */
    ERROR(-1, false);

    /**
     * 状 态 码
     */
    @Getter
    private final int code;

    /**
     * 携 带 消 息
     */
    @Getter
    private final boolean success;

    /**
     * 构 造 方 法
     */
    ResponseCodeEnum(int code, boolean success) {
        this.code = code;
        this.success = success;
    }

}
