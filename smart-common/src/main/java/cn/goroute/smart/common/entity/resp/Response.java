package cn.goroute.smart.common.entity.resp;

import cn.goroute.smart.common.enums.ResponseCodeEnum;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 返回数据
 * @author Alickx
 */
@ToString
@Data
public class Response<T> implements Serializable {

    /**
     * 状态码
     */
    private int code;

    /**
     * 返回消息
     */
    private boolean success;

    /**
     * 返回数据
     */
    private T data;

    public Response() {
    }

    public static <T> Response<T> success() {
        return Response.success(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.isSuccess(), null);
    }

    public static <T> Response<T> failure() {
        return Response.failure(ResponseCodeEnum.FAILURE.getCode(), ResponseCodeEnum.FAILURE.isSuccess(), null);
    }

    public static <T> Response<T> error() {
        return Response.error(ResponseCodeEnum.ERROR.getCode(), ResponseCodeEnum.ERROR.isSuccess(), null);
    }

    public static <T> Response<T> success(T data) {
        return Response.success(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.isSuccess(), data);
    }

    public static <T> Response<T> failure(T data) {
        return Response.failure(ResponseCodeEnum.FAILURE.getCode(), ResponseCodeEnum.FAILURE.isSuccess(), data);
    }

    public static <T> Response<T> error(T data) {
        return Response.error(ResponseCodeEnum.ERROR.getCode(), ResponseCodeEnum.ERROR.isSuccess(), null);
    }


    /**
     * 成功操作, 携带自定义状态码、消息、数据
     */
    public static <T> Response<T> success(int code, boolean success, T data) {
        Response<T> result = new Response<T>();
        result.setCode(code);
        result.setSuccess(success);
        result.setData(data);
        return result;
    }

    /**
     * 失败操作, 携带自定义状态码、消息、数据
     */
    public static <T> Response<T> failure(int code, boolean success, T data) {
        Response<T> result = new Response<T>();
        result.setCode(code);
        result.setSuccess(success);
        result.setData(data);
        return result;
    }

    /**
     * 错误操作, 携带自定义状态码、消息、数据
     */
    public static <T> Response<T> error(int code, boolean success, T data) {
        Response<T> result = new Response<T>();
        result.setCode(code);
        result.setSuccess(success);
        result.setData(data);
        return result;
    }
}
