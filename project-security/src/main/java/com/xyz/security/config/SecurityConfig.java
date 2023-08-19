package com.xyz.security.config;

import com.xyz.security.filter.JwtAuthenticationTokenFilter;
import com.xyz.security.handler.AuthenticationEntryPointHandler;
import com.xyz.security.handler.SecurityAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Spring Security 配置类
 *
 * @author ZKKzs
 **/
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    /**
     * 自己编写的 token 过滤器
     */
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    /**
     * 自己编写的认证失败处理器
     */
    @Autowired
    private AuthenticationEntryPointHandler authenticationEntryPointHandler;

    /**
     * 自己编写的权限认证处理器
     */
    @Autowired
    private SecurityAccessDeniedHandler accessDeniedHandler;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(
                        csrf -> csrf
                                // 关闭csrf
                                .disable()
                )
                // 不通过session获取SecurityContext
                .sessionManagement(
                        sessionManagement -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        request -> request
                                // todo: add path
                                // 这些接口都是匿名通过
                                .requestMatchers("/account/**", "/resource/**", "/user/forget", "/access/**").permitAll()
                                // todo: add path
                                // 只有 normal admin 权限的人才可以访问 db_admin中的level字段
                                .requestMatchers("/user/**").hasAnyAuthority("normal", "admin")
                                // todo: add path
                                // 只有 admin 权限的人才可以访问 db_admin中的level字段
                                .requestMatchers("/admin/**").hasAnyAuthority("admin")
                                // 任何没有匹配上的其他的 url请求，只需要用户被验证
                                .anyRequest().authenticated())
                // 将自己写的 token过滤器加在 UsernamePasswordAuthenticationFilter之前
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        exceptionHandling -> exceptionHandling
                                // 认证失败处理器
                                .authenticationEntryPoint(authenticationEntryPointHandler)
                                // 授权失败处理器
                                .accessDeniedHandler(accessDeniedHandler)
                )
                // 允许跨域
                .cors(withDefaults())
                .build();
    }
}
