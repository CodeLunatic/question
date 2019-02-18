package com.cy.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "一道题目")
public class ExamPaper extends BaseRowModel implements Serializable {
    @ApiModelProperty(value = "题目的Id，批卷使用", name = "questionId", required = true, example = "6e8c6dac9379437b8890f0a658fe8292")
    private String questionId;

    @ExcelProperty(index = 0)
    @ApiModelProperty(value = "知识点", name = "knowledgePoint", example = "来源于教学大纲")
    private String knowledgePoint;

    @ExcelProperty(index = 1)
    @ApiModelProperty(value = "试题类型", name = "type", required = true, example = "单选题")
    private String type;

    @ExcelProperty(index = 2)
    @ApiModelProperty(value = "题目", name = "question", required = true, example = "下面选项中正确的是?")
    private String question;

    @ExcelProperty(index = 3)
    @ApiModelProperty(value = "选项A", name = "optionA", example = "A.选项A")
    private String optionA;

    @ExcelProperty(index = 4)
    @ApiModelProperty(value = "选项B", name = "optionB", example = "B.选项B")
    private String optionB;

    @ExcelProperty(index = 5)
    @ApiModelProperty(value = "选项C", name = "optionC", example = "C.选项C")
    private String optionC;

    @ExcelProperty(index = 6)
    @ApiModelProperty(value = "选项D", name = "optionD", example = "D.选项D")
    private String optionD;

    @ExcelProperty(index = 7)
    @ApiModelProperty(value = "选项E", name = "optionE", example = "E.选项E")
    private String optionE;

    @ExcelProperty(index = 8)
    @ApiModelProperty(value = "选项F", name = "optionF", example = "F.选项F")
    private String optionF;

    @ExcelProperty(index = 9)
    @ApiModelProperty(value = "答案", name = "answer", required = true, example = "C")
    private String answer;

    @ExcelProperty(index = 10)
    @ApiModelProperty(value = "试题解析", name = "explain", example = "改题目考察了XXXXXX")
    private String explain;

    @ExcelProperty(index = 11)
    @ApiModelProperty(value = "出题人", name = "topicMaker", example = "张三")
    private String topicMaker;

    @ApiModelProperty(value = "我的答案", name = "myAnswer", example = "A")
    private String myAnswer;

    @ApiModelProperty(value = "题目来源", name = "from", example = "D:/CoreJava/CoreJava-01.xlsx")
    private String from;
}
