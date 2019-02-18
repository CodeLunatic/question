package com.cy.controller;

import com.cy.service.IExamFileListService;
import entity.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping(value = "examFileList", method = RequestMethod.GET)
@Api(tags = "文件列表", description = "试卷文件列表的查询操作")
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
    @ApiOperation("文件列表查询和分页")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "文件名", name = "fileName", dataType = "string"),
            @ApiImplicitParam(value = "当前页", name = "page", dataType = "integer", defaultValue = "1"),
            @ApiImplicitParam(value = "每页的数据量", name = "size", dataType = "integer", defaultValue = "10")
    })
    public PageResult findPage(String fileName, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        // 根据文件名查找文件列表
        return examPaperService.getFileList(fileName, page, size);
    }
}
