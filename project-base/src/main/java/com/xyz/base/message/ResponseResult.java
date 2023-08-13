package com.xyz.base.message;

import lombok.Data;

/**
 * @author ZKKzs
 **/
@Data
public class ResponseResult<T> {

    private String code;

    private String message;

    private T data;

    public static <T> ResponseResult<T> success(T data) {
        return response(ResultCode.SUCCESS, data);
    }

    public static <T> ResponseResult<T> success() {
        return response(ResultCode.SUCCESS);
    }

    public static <T> ResponseResult<T> fail(ResultCode code, T data) {
        return response(code, data);
    }

    public static <T> ResponseResult<T> fail(ResultCode code) {
        return response(code, null);
    }

    public static <T> ResponseResult<T> fail() {
        return response(ResultCode.ERROR);
    }

    public static <T> ResponseResult<T> response(ResultCode code, T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(code.code());
        result.setMessage(code.message());
        result.setData(data);
        return result;
    }

    public static <T> ResponseResult<T> response(ResultCode code) {
        return response(code, null);
    }

}
