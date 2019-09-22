package com.ibicd.company;

import com.hrm.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName CompanyApplication
 * @Description 企业启动类
 * @Author Julie
 * @Date 2019/9/16 7:37
 * @Version 1.0
 */
@SpringBootApplication(scanBasePackages = "com.ibicd")
@EntityScan("com.ibicd")
public class CompanyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanyApplication.class, args);
    }

    @Bean
    public IdWorker idWorkder() {
        return new IdWorker(1, 1);
    }
}
