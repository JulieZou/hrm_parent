package com.ibicd.system;

import com.ibicd.common.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @ClassName SystemConfig
 * @Description 系统配置类
 * @Author Julie
 * @Date 2019/9/26 22:25
 * @Version 1.0
 */
//@Configuration
//@Import(JwtInterceptor.class)
public class SystemConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JwtInterceptor interceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(interceptor).addPathPatterns("/**") //要拦截的路径
                .excludePathPatterns("/login"); //不拦截的路径

    }


}
