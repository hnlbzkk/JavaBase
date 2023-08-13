package com.xyz.security.config;

import com.xyz.security.model.converter.AccountConverter;
import com.xyz.security.utils.JwtUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * 将静态工具类中的属性 @Autowired
 *
 * @author ZKKzs
 **/
@Component
public class AutowireStaticSmartInitializingSingleton implements SmartInitializingSingleton {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Override
    public void afterSingletonsInstantiated() {
        beanFactory.autowireBean(new JwtUtils());
        beanFactory.autowireBean(new AccountConverter());
    }
}
