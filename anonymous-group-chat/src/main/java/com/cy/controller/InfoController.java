package com.cy.controller;

import com.cy.data.OnlineCount;
import com.cy.service.RandomUserInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 得到用户信息的Controller
 */
@RestController
@RequestMapping("chat")
public class InfoController {

    /**
     * 获取一个随机的用户名
     *
     * @param request IP
     * @return 用户的名 + IP
     * Todo 这种方式不恰当，因为前台可以通过修改Cookie的形式来进行IP的修改
     * 还可以自定义用户名，这里的设计思路需要改变
     */
    @PostMapping(value = "username")
    public String getRandomUsername(HttpServletRequest request) {
        return RandomUserInfo.getRandomUserName() + " (" + request.getRemoteAddr() + ")";
    }

    /**
     * 得到随机头像
     *
     * @return 随机头像的地址
     */
    @PostMapping(value = "avatar")
    public String getRandomAvatar() {
        return RandomUserInfo.getRandomAvatar();
    }

    /**
     * 得到在线的人数
     *
     * @return 在线的人数
     */
    @PostMapping(value = "getOnlineCount")
    public synchronized int getOnlineCount() {
        return OnlineCount.getOnlineCount();
    }
}
