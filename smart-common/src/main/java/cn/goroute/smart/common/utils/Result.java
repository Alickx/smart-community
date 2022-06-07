package cn.goroute.smart.common.utils;

import lombok.ToString;

import java.util.HashMap;

/**
 * 返回数据
 * @author Alickx
 */
@ToString
public class Result extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public Result() {
        put("code", 0);
        put("message", "success");
    }

    public static Result error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static Result error(String message) {
        return error(500, message);
    }

    public static Result error(long code, String message) {
        Result r = new Result();
        r.put("code", code);
        r.put("message", message);
        return r;
    }

    public static Result ok() {
        return new Result();
    }

    public static Result ok(String message) {
        Result r = new Result();
        r.put("message", message);
        return r;
    }


    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
