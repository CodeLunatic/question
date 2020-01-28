package com.cy.controller;

import com.cy.pojo.ExerciseInfo;
import com.cy.service.ICheckAnswerService;
import com.cy.service.IExamPaperService;
import com.cy.utils.JacksonUtil;
import entity.ExamPaperResult;
import entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 试卷的所有的操作【包括查找试卷，批卷，错题本】
 * 【状态：已测试2018年12月1日08:32:59】
 * By CY
 * Date 2018/11/26 23:27
 */
@RestController
@RequestMapping(value = "examPaper")
@Api(tags = "试卷操作", description = "一份试卷的展示和批卷操作")
public class ExamPaperController {

    @Resource
    private IExamPaperService examPaperService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private ICheckAnswerService checkAnswerService;

    /**
     * 获取试卷列表JSON的形式返回给前台
     * 【状态：已测试2018年11月27日23:02:01】
     *
     * @return 试卷的JSON串
     */
    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ApiOperation("根据文件的Id查找所有的题目")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "文件的Id", name = "fileId", dataType = "string", required = true),
            @ApiImplicitParam(value = "用户要做什么，可选值为 lookAtQuestion 和 doExercise (默认)", name = "whatToDo", dataType = "string")
    })
    public ExamPaperResult findAll(@RequestParam(value = "fileId") String fileId, String whatToDo) {
        try {
            /*
                动态过滤属性，在不同的页面上显示不同的属性
                比如说考试页面就不能显示答案
                比如说看题界面就必须显示答案
            */
            JacksonUtil jacksonUtil = dynamicFilterField(whatToDo);
            // 获取过滤后的JSON字符串
            String examPaperJSON = jacksonUtil.readAsString(examPaperService.getExamPaper(fileId));
            // 重新封装JSON到List中
            return new ExamPaperResult(true, jacksonUtil.json2List(examPaperJSON), "获取试题信息成功");
        } catch (IOException e) {
            return new ExamPaperResult(false, null, e.getMessage());
        }
    }

    /**
     * 动态过滤属性，在不同的页面上显示不同的属性
     * 【状态：已测试2018年11月29日22:50:44】
     *
     * @param whatToDo 用户的行为
     * @return 返回过滤后的JSON操作工具类对象
     */
    private JacksonUtil dynamicFilterField(String whatToDo) {
        JacksonUtil jacksonUtil;
        if ("lookAtQuestion".equals(whatToDo)) {
            jacksonUtil = JacksonUtil.newInstance().filter("exercisePaperFilter", "cellStyleMap");
        } else { // 过滤JSON，目的是避免在做题页面拿到不该拿到的字段，比如说答案字段
            jacksonUtil = JacksonUtil.newInstance().filter("exercisePaperFilter", "cellStyleMap", "answer", "explain", "myAnswer", "from");
        }
        return jacksonUtil;
    }

    /**
     * 批卷方法
     * 【状态：已测试2018年12月1日08:42:00】
     *
     * @param exerciseInfos 用户的答案
     * @param fileId        试卷的Id
     * @return 返回批卷结果
     */
    @RequestMapping(value = "checkAnswer", method = RequestMethod.POST)
    @ApiOperation("批卷方法")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "用户的答案列表", name = "exerciseInfos", dataType = "list", required = true),
            @ApiImplicitParam(value = "文件的Id", name = "fileId", dataType = "string", required = true)
    })
    public Result checkAnswer(@RequestBody List<ExerciseInfo> exerciseInfos, String fileId) {
        if (fileId == null || "".equals(fileId) || fileId.length() != 32)
            return new Result(false, "参数传递错误，无法进行批卷，请检查");
        try {
            checkAnswerService.checkAnswer(exerciseInfos, fileId, request.getRemoteAddr());
            return new Result(true, "批卷结束");
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }
}
