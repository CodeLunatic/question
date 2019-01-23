package com.cy.service;

import entity.PageResult;

/**
 * 此类用来进行文件列表的服务
 * 【状态：已测试2018年12月1日09:33:17】
 */
public interface IExamFileListService {

    /**
     * 获取文件列表
     * 读取题库文件夹中的全部Excel表格的名字和具体的位置，并为每一个文件赋予一个ID
     * 【状态：已测试2018年11月27日22:58:57】
     *
     * @return 返回考试文件的信息列表
     */
    PageResult getFileList(String fileName, int page, int size);


}
