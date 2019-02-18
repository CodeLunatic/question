package entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 试卷查询结果的封装类
 * 【状态：已测试2018年12月1日09:40:08】
 * By CY
 * Date 2018/11/27 14:00
 */
@ApiModel(description = "查询出来的试卷的实体类")
public class ExamPaperResult {

    @ApiModelProperty(value = "文件是否存在", name = "exist", required = true, example = "true")
    private boolean exist;

    @ApiModelProperty(value = "解析后的每道题目", name = "examPaperList", required = true)
    private List examPaperList;

    @ApiModelProperty(value = "页面中的提示信息", name = "message", required = true, example = "查询成功")
    private String message;

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
