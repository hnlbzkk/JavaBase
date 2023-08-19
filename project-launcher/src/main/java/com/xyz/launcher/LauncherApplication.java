package com.xyz.launcher;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
// todo: modify packages path
@ComponentScan(basePackages={"com.xyz.web","com.xyz.base","com.xyz.security", "com.xyz.db"})
@MapperScan(value = {"com.xyz.security.mapper"/*, "com.xyz.web.mapper"*/})
//添加此注解，开启AOP
@EnableAspectJAutoProxy
public class LauncherApplication {

    public static void main(String[] args) {
        SpringApplication.run(LauncherApplication.class, args);
    }

}
