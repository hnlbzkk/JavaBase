package com.xyz.base.handler;

import com.xyz.base.exception.ServiceException;
import com.xyz.base.message.ResponseResult;
import com.xyz.base.message.ResultCode;
import com.xyz.base.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.xyz.base.message.ResponseResult.fail;

/**
 * @author ZKKzs
 **/
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice {
    private final Logger logger = LoggerFactory.getLogger(ResponseAdvice.class);

    @Autowired
    HttpServletRequest httpServletRequest;


    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        //返回对象封装
        if (body instanceof ResponseResult<?>) {
            // 被exceptionHandler处理过了，直接返回
            return body;
        } else {
            return ResponseResult.success(body);
        }
    }

    /**
     *
     * 异常日志记录
     *
     * @param e exception
     */
    private void logErrorRequest(Exception e) {
        logger.error(LogUtils.error(e.getMessage()));
    }

    /**
     * 参数校验异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseResult<?> methodArgumentNotValid(MethodArgumentNotValidException exception) {
        logErrorRequest(exception);
        return fail(ResultCode.PARAM_INVALID);
    }

    /**
     * 参数格式有误
     *
     * @param exception
     * @return
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    protected ResponseResult<?> typeMismatch(Exception exception) {
        logErrorRequest(exception);
        return fail(ResultCode.PARAM_TYPE);
    }

    /**
     * 缺少参数
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseResult<?> missingServletRequestParameter(MissingServletRequestParameterException exception) {
        logErrorRequest(exception);
        return fail(ResultCode.PARAM_NULL);
    }

    /**
     * 不支持的请求类型
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseResult<?> httpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception) {
        logErrorRequest(exception);
        return fail(ResultCode.PARAM_UNSUPPORTED);
    }

    /**
     * 业务层异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    protected ResponseResult<?> serviceException(ServiceException exception) {
        logErrorRequest(exception);
        return fail(exception.getCode());
    }

    /**
     * 其他异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler({HttpClientErrorException.class, IOException.class, Exception.class})
    protected ResponseResult<?> commonException(Exception exception) {
        logErrorRequest(exception);
        return fail(ResultCode.ERROR);
    }

}
