package com.xyz.base.utils;

import com.xyz.base.message.ResultCode;
import com.xyz.base.param.LogParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Objects;


/**
 * @author ZKKzs
 **/
public class LogUtils {

    private static final HttpServletRequest REQUEST = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

    public static String error(ResultCode code) {
        return new LogParam(
                DateTimeUtil.getCurrentDate(),
                REQUEST.getRequestURI(),
                REQUEST.getMethod(),
                REQUEST.getQueryString(),
                code.code(),
                code.message(),
                REQUEST.getRemoteAddr(),
                REQUEST.getRemoteHost()
        ).toString();
    }

    public static String error(String message) {
        return new LogParam(
                DateTimeUtil.getCurrentDate(),
                REQUEST.getRequestURI(),
                REQUEST.getMethod(),
                REQUEST.getQueryString(),
                ResultCode.ERROR.code(),
                message,
                REQUEST.getRemoteAddr(),
                REQUEST.getRemoteHost()
        ).toString();
    }

    public static String success() {
        return new LogParam(
                DateTimeUtil.getCurrentDate(),
                REQUEST.getRequestURI(),
                REQUEST.getMethod(),
                REQUEST.getQueryString(),
                ResultCode.SUCCESS.code(),
                ResultCode.SUCCESS.message(),
                REQUEST.getRemoteAddr(),
                REQUEST.getRemoteHost()
        ).toString();
    }

}
