package com.cy.controller;

import com.cy.service.IScoreListService;
import entity.PageResult;
import entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 用户分数列表的操作
 * 【状态：已测试2018年12月1日08:47:40】
 * By CY
 * Date 2018/11/30 0:36
 */
@RestController
@RequestMapping(value = "scoreList", method = RequestMethod.GET)
@Api(tags = "分数列表", description = "分数列表的操作")
public class ScoreListController {

    @Resource
    private HttpServletRequest request;

    @Resource
    private IScoreListService scoreListService;

    /**
     * 对用户的分数进行分页展示和模糊查询
     * 【状态：已测试2018年12月1日08:48:39】
     *
     * @param date 根据日期进行查询
     * @param page 当前页
     * @param size 每页的数据条数
     * @return 分数列表分页的结果实体类
     */
    @RequestMapping("findPage")
    @ApiOperation("查询所有的分数列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "查询的日期", name = "date", dataType = "date"),
            @ApiImplicitParam(value = "当前页", name = "page", dataType = "integer", defaultValue = "1"),
            @ApiImplicitParam(value = "每页的条目数", name = "size", dataType = "integer", defaultValue = "10")
    })
    public PageResult findPage(Date date, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return scoreListService.findPage(date, page, size, request.getRemoteAddr());
    }

    /**
     * 清空所有的成绩列表
     * 【状态：已测试2018年12月1日08:50:28】
     *
     * @return 清空的状态
     */
    @RequestMapping("deleteAll")
    @ApiOperation("清空分数列表")
    public Result deleteAll() {
        boolean b = scoreListService.deleteAll(request.getRemoteAddr());
        if (b) return new Result(true, "数据已清空!");
        return new Result(false, "数据清空失败!");
    }

    /**
     * 根据Id删除分数列表中的一条数据
     * 【状态：已测试2018年12月1日08:51:36】
     *
     * @param id 分数列表的Id
     * @return 返回操作的结果
     */
    @RequestMapping("delete")
    @ApiOperation("删除单条分数信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "分数的Id", name = "id", dataType = "string")
    })
    public Result delete(String id) {
        String ip = request.getRemoteAddr();
        boolean delete = scoreListService.delete(id, ip);
        if (delete) return new Result(true, "删除成功!");
        return new Result(false, "删除失败!");
    }

    /**
     * 此方法用来把所有的考试成绩导出到Excel中
     *
     * @param response 响应流
     * @throws IOException 如果操作失败
     */
    @RequestMapping("exportExcel")
    @ApiOperation("导出到Excel文件中")
    public void exportExcel(HttpServletResponse response) throws IOException {
        String fileName = new String("分数信息.xlsx".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        ServletOutputStream outputStream = response.getOutputStream();
        scoreListService.exportExcel(outputStream, request.getRemoteAddr());
    }
}
