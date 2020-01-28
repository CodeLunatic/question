package com.cy.conf;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 这个类的作用是项目启动后自动打开浏览器访问项目
 * By CY
 * Date 2018/11/26 12:50
 */
@Configuration
public class ApplicationInit implements CommandLineRunner {

    /**
     * 运行项目后要进行的操作【已测试2018年11月27日23:03:49】
     *
     * @param args 参数
     * @throws Exception 异常
     */
    @Order(0) // 运行的顺序，数字越小优先级越高
    @Override
    public void run(String... args) throws Exception {
        // 自动打开默认浏览器访问项目，当前只有Windows平台可用
        Runtime.getRuntime().exec("cmd /c start http://127.0.0.1:8080/");
    }
}
