package com.xyz.security.service;

import com.xyz.security.model.param.LoginParam;
import com.xyz.security.model.param.RegisterParam;
import com.xyz.security.vo.LoginVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZKKzs
 */
public interface AccountService {

    LoginVO login(LoginParam param);

    boolean register(RegisterParam param);

}
