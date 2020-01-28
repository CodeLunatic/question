package com.cy.dao;

import com.cy.pojo.ExamFileInfo;

import java.util.List;

/**
 * 题库中文件列表操作
 * By CY
 * 已阅
 * Date 2018/11/27 15:39
 */
public interface IExamFileListDao {

    /**
     * 得到所有文件列表的集合
     * 【状态：已测试2018年11月28日19:12:55】
     *
     * @return 文件列表集合
     */
    List<ExamFileInfo> queryListByFileName(String fileName);

    /**
     * 根据文件的Id来获得文件的真实的路径
     * 【状态：已测试2018年11月27日23:00:05】
     *
     * @param fileId 文件的Id
     * @return 文件的真实的路径
     */
    ExamFileInfo getFileInfoByFileId(String fileId);
}
