package cn.goroute.smart.common.exception;

public enum BizCodeEnum {

    UNKNOWN_EXCEPTION(10000, "系统未知异常"),
    VALID_EXCEPTION(10001, "参数格式校验失败"),
    DEFAULT_PARAM(11000,"请求参数缺失"),
    ERROR_REQUEST_METHOD(10002,"请求方法错误"),
    NOT_LOGIN_EXCEPTION(12000,""),
    NOT_FOUND_MEMBER(13000,"查询用户失败，没有该用户"),
    NOT_DATA(14000,"查询数据失败"),
    POST_NOT_EXIST(9000,"文章不存在" ),
    USER_NOT_EXIST(9001,"用户不存在" ),
    ILLEGAL_TEXT(8000,"检测到违规词语" );


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
