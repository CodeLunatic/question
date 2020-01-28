package com.cy.data;

import org.springframework.stereotype.Component;

@Component
public class OnlineCount {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount;

    /**
     * 增加在线人数
     */
    public static synchronized void addOnlineCount() {
        onlineCount++;
    }

    /**
     * 减少在线人数
     */
    public static synchronized void subOnlineCount() {
        onlineCount--;
    }

    /**
     * 得到在线人数
     *
     * @return 在线人数
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
}
