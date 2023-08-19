package com.xyz.security.controller;

import com.xyz.base.annotation.LogApi;
import com.xyz.base.message.ResponseResult;
import com.xyz.base.message.ResultCode;
import com.xyz.security.model.param.LoginParam;
import com.xyz.security.model.param.RegisterParam;
import com.xyz.security.service.AccountService;
import com.xyz.security.vo.LoginVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ZKKzs
 * @since 2023/8/13
 **/
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    @LogApi
    public ResponseResult register(@RequestBody @Valid RegisterParam param) {
        boolean isSuccess = accountService.register(param);
        return isSuccess ?
                ResponseResult.success() :
                ResponseResult.fail(ResultCode.USER_REGISTER);
    }

    @GetMapping("/login")
    @LogApi
    public ResponseResult<LoginVO> login(@RequestBody @Valid LoginParam param) {
        LoginVO result = accountService.login(param);
        return result != null ?
                ResponseResult.success(result) :
                ResponseResult.fail(ResultCode.USER_LOGIN_ERROR);
    }
}
