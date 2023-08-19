package com.xyz.security.filter;

import com.alibaba.fastjson.JSON;
import com.xyz.base.message.ResultCode;
import com.xyz.base.utils.LogUtils;
import com.xyz.db.redis.RedisUtil;
import com.xyz.security.model.entity.LoginUser;
import com.xyz.security.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.xyz.base.constant.Constant.LOGIN_PREFIX;

/**
 * token验证过滤器
 *
 * @author ZKKzs
 **/
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1.获取 token
        String token = request.getHeader("token");
        // 没有token则直接放行
        if (token == null || token.isEmpty() || token.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2.解析 token
        Claims claims;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            logger.error(LogUtils.error(ResultCode.USER_ACCOUNT_VERIFY));
            throw new RuntimeException("token验证失败");
        }
        LoginUser user = JSON.parseObject(claims.getSubject(), LoginUser.class);

        // 3.从 redis 中获取信息
        String redisKey = LOGIN_PREFIX + user.getUser().getEmail();
        LoginUser redisUser = redisUtil.getCacheObject(redisKey);
        if (Objects.isNull(redisUser)) {
            logger.error(LogUtils.error("用户未登录"));
            throw new RuntimeException("用户未登录");
        }

        // 4.存入 SecurityContextHolder
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 5.放行
        filterChain.doFilter(request, response);
    }
}
