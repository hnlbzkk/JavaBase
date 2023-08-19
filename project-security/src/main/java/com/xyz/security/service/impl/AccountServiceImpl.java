package com.xyz.security.service.impl;

import com.alibaba.fastjson.JSON;
import com.xyz.base.exception.ServiceException;
import com.xyz.base.message.ResultCode;
import com.xyz.base.utils.LogUtils;
import com.xyz.db.redis.RedisUtil;
import com.xyz.security.mapper.DbAdminMapper;
import com.xyz.security.mapper.DbUserMapper;
import com.xyz.security.model.converter.AccountConverter;
import com.xyz.security.model.entity.AdminEntity;
import com.xyz.security.model.entity.LoginUser;
import com.xyz.security.model.entity.UserEntity;
import com.xyz.security.model.param.LoginParam;
import com.xyz.security.model.param.RegisterParam;
import com.xyz.security.service.AccountService;
import com.xyz.security.utils.JwtUtils;
import com.xyz.security.vo.LoginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.xyz.base.constant.Constant.LOGIN_PREFIX;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ZKKzs
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DbUserMapper userMapper;

    @Autowired
    private DbAdminMapper adminMapper;

    @Autowired
    private RedisUtil redisCache;

    @Override
    public LoginVO login(LoginParam param) {
        try {
            // 1.Authentication 进行用户认证
            Authentication token = new UsernamePasswordAuthenticationToken(param.email(), param.password());
            Authentication authenticate = authenticationManager.authenticate(token);
            // 为空说明验证不过
            if (Objects.isNull(authenticate)) {
                logger.error(LogUtils.error(ResultCode.USER_ACCOUNT_VERIFY));
                throw new ServiceException(ResultCode.USER_ACCOUNT_VERIFY);
            }

            // 2.验证通过，进行 jwt 设置
            LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
            loginUser.getUser().setPassword("");
            String json = JSON.toJSONString(loginUser);
            String email = loginUser.getUser().getEmail();

            // 3.把用户信息保存在redis中
            redisCache.setCacheObject(LOGIN_PREFIX + email, loginUser);

            // 4.返回 JWT 数据
            return new LoginVO(JwtUtils.createJWT(json));
        } catch (Exception e) {
            logger.error(LogUtils.error(e.getMessage()));
            logger.error(LogUtils.error(ResultCode.USER_LOGIN_ERROR));
            throw new ServiceException(ResultCode.USER_LOGIN_ERROR);
        }
    }

    public boolean register(RegisterParam param) {
        UserEntity user = AccountConverter.convertToUserEntity(param);

        try {
            userMapper.insert(user);
            AdminEntity admin = AccountConverter.convertToAdminEntity(user);
            adminMapper.insert(admin);
        } catch (Exception e) {
            logger.error(LogUtils.error(e.getMessage()));
            logger.error(LogUtils.error(ResultCode.USER_REGISTER));
            throw new ServiceException(ResultCode.USER_REGISTER);
        }

        return true;
    }
}
