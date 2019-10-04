package com.ibicd.system;

import com.ibicd.common.utils.IdWorker;
import com.ibicd.common.utils.JwtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

/**
 * @ClassName SystemApplication
 * @Description 系统启动类
 * @Author Julie
 * @Date 2019/9/22 12:56
 * @Version 1.0
 */
@SpringBootApplication(scanBasePackages = {"com.ibicd"})
@EntityScan(value = {"com.ibicd"})
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }

    @Bean
    public JwtUtils jwtUtils() {

        return new JwtUtils();
    }

    //解决no session
    @Bean
    public OpenEntityManagerInViewFilter openEntityManagerInViewFilter() {

        return new OpenEntityManagerInViewFilter();
    }

}
