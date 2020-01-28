package com.cy.service;

import com.cy.pojo.ExamPaper;

import java.io.IOException;
import java.util.List;

/**
 * 试卷的服务层，做所有和试卷有关的操作
 * 包括刷题和看题和错题
 * 【状态：已测试2018年12月1日09:33:56】
 * By CY
 * Date 2018/11/26 23:48
 */
public interface IExamPaperService {

    /**
     * 得到试卷实体类
     * 根据路径名获取整份试卷
     * 【状态：已测试2018年11月27日22:58:30】
     *
     * @throws IOException 如果读取文件或者路径什么的有问题的话
     */
    List<ExamPaper> getExamPaper(String fileId) throws IOException;

    /**
     * 得到整个错题本数据
     * 【状态：已测试2018年11月30日00:34:00】
     *
     * @return 错题本
     */
    List<ExamPaper> getWrongTopicList(String ip);

    /**
     * 根据题目的Id来删除错题本上的一道题
     * 【状态：已测试2018年12月1日09:34:08】
     *
     * @param questionId 题目的Id
     * @return 是否删除成功
     */
    boolean deleteWrongTopicByQuestionId(String questionId, String ip);

    /**
     * 得到根据一个分数的Id得到这个分数对应的错题
     * 【状态：已测试2018年12月1日09:34:20】
     *
     * @param scoreId 分数的Id
     * @return 分数Id对应的错题列表
     */
    List<ExamPaper> getOnceWrongTopicList(String scoreId);
}
