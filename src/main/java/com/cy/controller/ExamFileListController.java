package com.cy.controller;

import com.cy.service.IExamFileListService;
import entity.PageResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用来扫描用户的文件列表
 * 【状态：已完成2018年12月1日08:10:32】
 * <p>
 * By CY
 * Date 2018/11/28 18:25
 */
@RestController
@RequestMapping(value = "examFileList")
public class ExamFileListController {

    @Resource
    private IExamFileListService examPaperService;

    /**
     * 获取试卷列表JSON的形式返回给前台
     * 【状态：已测试2018年11月29日22:38:37】
     *
     * @return 试卷的JSON串
     */
    @RequestMapping("findPage")
    public PageResult findPage(String fileName, int page, int size) {
        // 根据文件名查找文件列表
        return examPaperService.getFileList(fileName, page, size);
    }
}
