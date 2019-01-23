package entity;

import java.util.List;

/**
 * 试卷查询结果的封装类
 * 【状态：已测试2018年12月1日09:40:08】
 * By CY
 * Date 2018/11/27 14:00
 */
public class ExamPaperResult {

    private boolean exist; //文件是否存在

    private List examPaperList; // 解析后的每道题目

    private String message; // 页面中的提示信息

    public ExamPaperResult(boolean exist, List examPaperList, String message) {
        this.exist = exist;
        this.examPaperList = examPaperList;
        this.message = message;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public List getExamPaperList() {
        return examPaperList;
    }

    public void setExamPaperList(List examPaperList) {
        this.examPaperList = examPaperList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
