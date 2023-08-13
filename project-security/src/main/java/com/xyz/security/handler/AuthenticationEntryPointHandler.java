package com.xyz.security.handler;

import com.alibaba.fastjson.JSON;
import com.xyz.base.message.ResponseResult;
import com.xyz.base.message.ResultCode;
import com.xyz.security.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器
 *
 * @author ZKKzs
 **/
@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String json = JSON.toJSONString(ResponseResult.fail(ResultCode.USER_ACCOUNT_VERIFY));
        WebUtils.renderString(response, json);
    }

}
