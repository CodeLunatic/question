package com.cy.service;

import entity.PageResult;

import java.io.OutputStream;
import java.util.Date;

/**
 * 分数列表操作
 * 【状态：已测试2018年12月1日09:34:53】
 * By CY
 * Date 2018/11/29 18:12
 */
public interface IScoreListService {

    /**
     * 对分数列表的分页
     * 【状态：已测试2018年12月1日09:35:01】
     *
     * @param date 日期
     * @param page 当前页
     * @param size 分页尺寸
     * @param ip   用户的唯一标识
     * @return 返回分页结果
     */
    PageResult findPage(Date date, int page, int size, String ip);

    /**
     * 根据Id删除用户的分数
     * 【状态：已测试2018年12月1日09:35:09】
     *
     * @param id 分数的Id
     * @param ip 用户的唯一标识
     * @return 返回删除是否成功
     */
    boolean delete(String id, String ip);

    /**
     * 清空分数列表
     * 【状态：已测试2018年12月1日09:35:19】
     *
     * @param ip 用户的唯一标识
     * @return 是否清空成功
     */
    boolean deleteAll(String ip);

    /**
     * 导出分数信息到Excel
     *
     * @param outputStream 输出流
     * @param ip           用户的唯一标识
     */
    void exportExcel(OutputStream outputStream, String ip);
}
