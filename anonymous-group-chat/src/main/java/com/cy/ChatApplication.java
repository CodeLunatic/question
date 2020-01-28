package com.cy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * By CY
 * Date 2018/11/24 12:10
 */
@SpringBootApplication
public class ChatApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    /**
     * 部署到Tomcat需要这个方法
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ChatApplication.class);
    }
}
