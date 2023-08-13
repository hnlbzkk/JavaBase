package com.xyz.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xyz.base.message.ResultCode;
import com.xyz.security.model.entity.AdminEntity;
import com.xyz.security.model.entity.LoginUser;
import com.xyz.security.model.entity.UserEntity;
import com.xyz.security.mapper.DbAdminMapper;
import com.xyz.security.mapper.DbUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 通过数据库查询对应的用户信息,设置security可通过
 * @author ZKKzs
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private DbUserMapper userMapper;

    @Autowired
    private DbAdminMapper adminMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 查询用户信息
        UserEntity user = userMapper.selectOne(new QueryWrapper<UserEntity>().eq("email", email));
        if (Objects.isNull(user)) {
            throw new RuntimeException(ResultCode.USER_NOT_EXIST.message());
        }
        // 设置对应的权限信息
        AdminEntity admin = adminMapper.selectOne(new QueryWrapper<AdminEntity>().eq("user_id", user.getId()));
        List<String> lists = List.of(admin.getAdminLevel());

        return new LoginUser(user, lists);
    }
}
