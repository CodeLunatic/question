package com.cy.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * 试卷的实体类【根据Excel表格中的12个字段，设置了12个属性】
 * 这个实体类是试卷的基础实体类
 * 【状态：已测试2018年11月27日22:59:20】
 * By CY
 * Date 2018/11/26 12:36
 */
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

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getKnowledgePoint() {
        return knowledgePoint;
    }

    public void setKnowledgePoint(String knowledgePoint) {
        this.knowledgePoint = knowledgePoint;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getOptionE() {
        return optionE;
    }

    public void setOptionE(String optionE) {
        this.optionE = optionE;
    }

    public String getOptionF() {
        return optionF;
    }

    public void setOptionF(String optionF) {
        this.optionF = optionF;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getTopicMaker() {
        return topicMaker;
    }

    public void setTopicMaker(String topicMaker) {
        this.topicMaker = topicMaker;
    }

    public String getMyAnswer() {
        return myAnswer;
    }

    public void setMyAnswer(String myAnswer) {
        this.myAnswer = myAnswer;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * 重新此方法是为了List求差集
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamPaper examPaper = (ExamPaper) o;
        return Objects.equals(knowledgePoint, examPaper.knowledgePoint) &&
                Objects.equals(type, examPaper.type) &&
                Objects.equals(question, examPaper.question) &&
                Objects.equals(optionA, examPaper.optionA) &&
                Objects.equals(optionB, examPaper.optionB) &&
                Objects.equals(optionC, examPaper.optionC) &&
                Objects.equals(optionD, examPaper.optionD) &&
                Objects.equals(optionE, examPaper.optionE) &&
                Objects.equals(optionF, examPaper.optionF) &&
                Objects.equals(answer, examPaper.answer) &&
                Objects.equals(explain, examPaper.explain) &&
                Objects.equals(topicMaker, examPaper.topicMaker);
    }

    @Override
    public int hashCode() {

        return Objects.hash(knowledgePoint, type, question, optionA, optionB, optionC, optionD, optionE, optionF, answer, explain, topicMaker);
    }

    /**
     * 一个为了产生MD5而被迫写的一个toString()
     *
     * @return
     */
    @Override
    public String toString() {
        return "ExamPaper{" +
                "questionId='" + questionId + '\'' +
                ", knowledgePoint='" + knowledgePoint + '\'' +
                ", type='" + type + '\'' +
                ", question='" + question + '\'' +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", optionC='" + optionC + '\'' +
                ", optionD='" + optionD + '\'' +
                ", optionE='" + optionE + '\'' +
                ", optionF='" + optionF + '\'' +
                ", answer='" + answer + '\'' +
                ", explain='" + explain + '\'' +
                ", topicMaker='" + topicMaker + '\'' +
                ", myAnswer='" + myAnswer + '\'' +
                ", from='" + from + '\'' +
                '}';
    }
}
