package com.cy.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 试卷的实体类【根据Excel表格中的12个字段，设置了12个属性】
 * 这个实体类是试卷的基础实体类
 * 【状态：已测试2018年11月27日22:59:20】
 * By CY
 * Date 2018/11/26 12:36
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonFilter("exercisePaperFilter")
public class ExamPaper extends BaseRowModel implements Serializable {
    // 题目的Id，批卷使用
    private String questionId;
    // 知识点
    @ExcelProperty(index = 0)
    private String knowledgePoint;
    // 试题类型
    @ExcelProperty(index = 1)
    private String type;
    // 题目
    @ExcelProperty(index = 2)
    private String question;
    // 选项A
    @ExcelProperty(index = 3)
    private String optionA;
    // 选项B
    @ExcelProperty(index = 4)
    private String optionB;
    // 选项C
    @ExcelProperty(index = 5)
    private String optionC;
    // 选项D
    @ExcelProperty(index = 6)
    private String optionD;
    // 选项E
    @ExcelProperty(index = 7)
    private String optionE;
    // 选项F
    @ExcelProperty(index = 8)
    private String optionF;
    // 答案
    @ExcelProperty(index = 9)
    private String answer;
    // 试题解析
    @ExcelProperty(index = 10)
    private String explain;
    // 出题人
    @ExcelProperty(index = 11)
    private String topicMaker;
    // 我的答案
    private String myAnswer;
    // 题目来源
    private String from;
}
