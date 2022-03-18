package cn.goroute.smart.common.exception;

public enum BizCodeEnum {

    UNKNOWN_EXCEPTION(10000, "系统未知异常"),
    VALID_EXCEPTION(10001, "参数格式校验失败"),
    DEFAULT_PARAM(11000,"请求参数缺失"),
    ERROR_REQUEST_METHOD(10002,"请求方法错误");

    private int code;
    private String message;
    BizCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }
    public String getMessage(){ return message; }
}
