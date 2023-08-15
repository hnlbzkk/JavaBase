package com.xyz.web.controller;

import com.xyz.base.annotation.AccessLimit;
import com.xyz.base.message.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试 IP拦截注解
 *
 * @author ZKKzs
 * @since 2023/8/14
 **/
@RestController
@RequestMapping("/access")
public class AccessLimitController {

    @GetMapping("/hello")
    @AccessLimit(second = 60, maxRequestCount = 1)
    public ResponseResult<String> accessLimitTest() {
        return ResponseResult.success("hello access.");
    }

}
