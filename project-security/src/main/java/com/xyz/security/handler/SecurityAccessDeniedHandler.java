package com.xyz.security.handler;

import com.alibaba.fastjson.JSON;
import com.xyz.base.message.ResponseResult;
import com.xyz.base.message.ResultCode;
import com.xyz.security.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限认证不通过
 *
 * @author ZKKzs
 * @since 2022/11/28
 **/
@Component
public class SecurityAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String json = JSON.toJSONString(ResponseResult.fail(ResultCode.USER_ACCOUNT_FORBIDDEN));
        WebUtils.renderString(response, json);
    }

}
