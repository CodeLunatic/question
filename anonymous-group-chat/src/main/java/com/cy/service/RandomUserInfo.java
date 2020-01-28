package com.cy.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 * 得到随机的匿名用户信息
 * By CY
 * Date 2018/11/24 14:05
 */
public class RandomUserInfo {

    /**
     * 头像属性文件的路径地址
     */
    private static final String RANDOM_AVATAR_FILE_PATH = "properties/randomAvatar.properties";

    /**
     * 随机用户名文件的路径地址
     */
    private static final String RANDOM_USERNAME_FILE_PATH = "properties/randomUsername.properties";

    /**
     * 得到随机的头像
     */
    public static String getRandomAvatar() {
        // 读取匿名头像属性文件
        InputStream resourceAsStream = RandomUserInfo.class.getClassLoader().getResourceAsStream(RANDOM_AVATAR_FILE_PATH);
        return getRandomValue(resourceAsStream);
    }

    /**
     * 得到随机的用户名
     */
    public static String getRandomUserName() {
        // 读取匿名头像属性文件
        InputStream resourceAsStream = RandomUserInfo.class.getClassLoader().getResourceAsStream(RANDOM_USERNAME_FILE_PATH);
        return getRandomValue(resourceAsStream);
    }

    /**
     * 得到随机值
     *
     * @param resourceAsStream 输入流
     * @return 返回一个随机值
     */
    private static String getRandomValue(InputStream resourceAsStream) {
        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
            resourceAsStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i = new Random(new Date().getTime()).nextInt(properties.size());
        return (String) properties.get(Integer.toString(i));
    }
}
