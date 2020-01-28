package com.cy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 启动类
 * By CY
 * Date 2018/11/16 16:39
 */
@EnableCaching
@SpringBootApplication
public class OfflineApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(OfflineApplication.class, args);
    }

    /**
     * 部署到Tomcat需要这个方法
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OfflineApplication.class);
    }
}
