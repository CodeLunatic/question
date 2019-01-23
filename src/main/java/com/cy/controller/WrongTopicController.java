package com.cy.controller;

import com.cy.pojo.ExamPaper;
import com.cy.service.IExamPaperService;
import com.cy.utils.JacksonUtil;
import entity.ExamPaperResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping(value = "wrongTopic")
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
    public ExamPaperResult findAll() {
        String ip = request.getRemoteAddr();
        // 获取错题本
        List<ExamPaper> wrongTopicList = examPaperService.getWrongTopicList(ip);
        // 如果错题本不为null或Empty，传递到前台
        if (wrongTopicList != null && wrongTopicList.size() != 0) {
            JacksonUtil jacksonUtil = JacksonUtil.newInstance().filter("exercisePaperFilter", "");
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
    public Result delete(String questionId) {
        String ip = request.getRemoteAddr();
        boolean b = examPaperService.deleteWrongTopicByQuestionId(questionId, ip);
        if (b) return new Result(true, "删除成功");
        return new Result(false, "删除失败");
    }
}
