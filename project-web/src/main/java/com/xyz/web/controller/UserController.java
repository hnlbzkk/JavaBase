package com.xyz.web.controller;

import com.xyz.base.message.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试用户权限API-security
 *
 * @author ZKKzs
 * @since 2023/8/14
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/hello")
    public ResponseResult<String> userHello() {
        return ResponseResult.success("hello user.");
    }
}
