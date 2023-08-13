package com.xyz.base.exception;


import com.xyz.base.message.ResultCode;

/**
 * @author ZKKzs
 * @since 2022/12/6
 **/
public class ServiceException extends RuntimeException{

    ResultCode code;

    public ServiceException() {
        super(ResultCode.ERROR.message());
        this.code = ResultCode.ERROR;
    }

    public ServiceException(ResultCode code) {
        super(code.message());
        this.code = code;
    }

    @Override
    public String getMessage() {
        return this.code.message();
    }

    public ResultCode getCode() {
        return this.code;
    }

}
