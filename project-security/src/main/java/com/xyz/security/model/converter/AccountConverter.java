package com.xyz.security.model.converter;

import com.xyz.security.model.entity.AdminEntity;
import com.xyz.security.model.entity.UserEntity;
import com.xyz.security.model.param.RegisterParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

/**
 * @author ZKKzs
 * @since 2023/8/14
 **/
public class AccountConverter {

    private static PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        AccountConverter.passwordEncoder = passwordEncoder;
    }

    public static UserEntity convertToUserEntity(RegisterParam param) {
        UserEntity result = new UserEntity();

        result.setUserName(param.name());
        result.setPassword(passwordEncoder.encode(param.password()));
        result.setPhone(param.phone());
        result.setEmail(param.email());
        result.setRegisterTime(LocalDateTime.now());

        return result;
    }

    public static AdminEntity convertToAdminEntity(UserEntity user) {
        AdminEntity result = new AdminEntity();

        result.setUserId(user.getId());
        // todo: mysql table: db_admin admin_level
        result.setAdminLevel("normal");
        result.setPermissionTime(LocalDateTime.now());

        return result;
    }
}
