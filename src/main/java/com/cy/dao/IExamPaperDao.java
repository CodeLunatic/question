package com.cy.dao;

import com.cy.pojo.ExamPaper;

import java.io.IOException;
import java.util.List;

/**
 * 试卷的数据层
 * 已阅
 * By CY
 * Date 2018/11/27 8:44
 */
public interface IExamPaperDao {

    /**
     * 获取试卷列表的数据层
     * 【状态：已测试2018年11月27日23:00:21】
     *
     * @param fileDir 文件的路径
     * @return 一张试卷的列表
     * @throws IOException 如果文件存在异常的话
     */
    List<ExamPaper> getExamPaperList(String fileDir) throws IOException;

    /**
     * 从硬盘的序列化文件中找到试题列表,用来批卷
     * 【状态：已测试2018年11月29日23:23:10】
     *
     * @param fileId 文件的Id
     * @return 试题列表
     */
    List<ExamPaper> getExamPaperListFromDisc(String fileId);
}
