package cn.goroute.smart.common.utils;

/**
 * 错误代码枚举类
 */
public enum RespCode {

    CAPTCHA_ERROR(10001);


    private final int code;

    RespCode(int code) {
        this.code = code;
    }

    public int getCode(){
        return code;
    }

}
