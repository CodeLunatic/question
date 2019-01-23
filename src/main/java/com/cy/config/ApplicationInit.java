package com.cy.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.io.File;

/**
 * 这个类的作用是项目启动后自动打开浏览器访问项目
 * 然后初始化程序所需要的文件夹
 * 【状态：已测试成功，2018年12月1日08:06:35】
 * By CY
 * Date 2018/11/26 12:50
 */
@Configuration
public class ApplicationInit implements CommandLineRunner {

    // 运行时文件保存的目录
    private static final String userDir = System.getProperty("user.dir");

    /**
     * 运行项目后要进行的操作【已测试2018年11月27日23:03:49】
     *
     * @param args 参数
     * @throws Exception 异常
     */
    @SuppressWarnings("all")
    @Order(0) // 运行的顺序，数字越小优先级越高
    @Override
    public void run(String... args) throws Exception {
        // 自动打开默认浏览器访问项目，当前只有Windows平台可用，如果想要其他系统兼容此项目，需要删除这句话
        Runtime.getRuntime().exec("cmd /c start http://127.0.0.1/");
        // 程序启动的时候创建需要的资源文件夹
        // 题库文件夹，用来保存用户的自定义题库
        File examPaper = new File(userDir + File.separator + "题库");
        if (!examPaper.exists()) examPaper.mkdirs();
        // 临时文件夹，用来保存用户的文件列表数据和临时缓存的题目数据
        File tempFile = new File(userDir + File.separator + "数据" + File.separator + "临时");
        if (!tempFile.exists()) tempFile.mkdirs();
        // 错题文件夹，用来保存用户的每一份错题
        File wrongTopic = new File(userDir + File.separator + "数据" + File.separator + "错题");
        if (!wrongTopic.exists()) wrongTopic.mkdirs();
    }
}
