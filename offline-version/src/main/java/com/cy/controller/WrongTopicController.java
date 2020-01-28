package com.cy.controller;

import com.cy.pojo.ExamPaper;
import com.cy.service.IExamPaperService;
import com.cy.utils.JacksonUtil;
import entity.ExamPaperResult;
import entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户错题列表的操作
 * By CY
 * Date 2018/11/29 13:29
 */
@RestController
@RequestMapping(value = "wrongTopic", method = RequestMethod.GET)
@Api(tags = "错题操作", description = "错题列表的操作")
public class WrongTopicController {

    @Resource
    private IExamPaperService examPaperService;

    @Resource
    private HttpServletRequest request;

    /**
     * 查找到所有的错题
     * 【状态：已测试2018年11月30日00:33:32】
     *
     * @return 错题列表
     */
    @RequestMapping("findAll")
    @ApiOperation("查询所有的错题")
    public ExamPaperResult findAll() {
        String ip = request.getRemoteAddr();
        // 获取错题本
        List<ExamPaper> wrongTopicList = examPaperService.getWrongTopicList(ip);
        // 如果错题本不为null或Empty，传递到前台
        if (wrongTopicList != null && wrongTopicList.size() != 0) {
            JacksonUtil jacksonUtil = JacksonUtil.newInstance().filter("exercisePaperFilter", "cellStyleMap");
            String wrongTopicListJSON = jacksonUtil.readAsString(wrongTopicList);
            return new ExamPaperResult(true, jacksonUtil.json2List(wrongTopicListJSON), "错题本获取成功");
        }
        // 提醒没有错题
        return new ExamPaperResult(false, wrongTopicList, "您没有错题哦，加油刷题吧!");
    }

    /**
     * 根据Id删除一道错题
     * 【状态：已测试2018年11月30日00:33:18】
     *
     * @param questionId 错题的Id
     * @return 删除结果
     */
    @RequestMapping("delete")
    @ApiOperation("通过题目的Id来删除错题")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "问题的Id", name = "questionId", dataType = "string")
    })
    public Result delete(String questionId) {
        String ip = request.getRemoteAddr();
        boolean b = examPaperService.deleteWrongTopicByQuestionId(questionId, ip);
        if (b) return new Result(true, "删除成功");
        return new Result(false, "删除失败");
    }

    /**
     * 根据分数的Id获取分数所对应的错题
     *
     * @param scoreId 分数的Id
     * @return 分数Id所对应的错题列表
     */
    @RequestMapping("getWrongTopicByScoreId")
    @ApiOperation("通过分数的Id来得到分数所对应的错误情况")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "分数Id", name = "scoreId", dataType = "string", required = true),
    })
    public ExamPaperResult getWrongTopicByScoreId(String scoreId) {
        if (scoreId == null || "".equalsIgnoreCase(scoreId) || scoreId.length() != 32)
            return new ExamPaperResult(false, null, "参数传递错误，无法获取错题列表");
        // 动态过滤属性，在不同的页面上显示不同的属性
        JacksonUtil jacksonUtil = JacksonUtil.newInstance().filter("exercisePaperFilter", "cellStyleMap");
        // 获取过滤后的JSON字符串
        String wrongTopicJSON = jacksonUtil.readAsString(examPaperService.getOnceWrongTopicList(scoreId));
        // 查询到的制定错题
        return new ExamPaperResult(true, jacksonUtil.json2List(wrongTopicJSON), "获取指定错题成功");
    }
}
