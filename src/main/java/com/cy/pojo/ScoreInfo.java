package com.cy.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 分数列表实体类
 * 【状态：已测试2018年12月1日09:17:21】
 * By CY
 * Date 2018/11/29 17:04
 */
public class ScoreInfo extends BaseRowModel implements Serializable {

    // 分数的Id
    private String id;
    // 试卷的名称
    @ExcelProperty(value = "试卷名称", index = 0)
    private String fileName;
    // 试卷的分数
    @ExcelProperty(value = "试卷分数", index = 1)
    private String score;
    // 提交的日期
    @ExcelProperty(value = "提交时间", index = 2, format = "yyyy年MM月dd日 HH点mm分ss秒")
    private Date date;
    // 文件的Id
    private String fileId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreInfo scoreInfo = (ScoreInfo) o;
        return Objects.equals(id, scoreInfo.id) &&
                Objects.equals(fileName, scoreInfo.fileName) &&
                Objects.equals(score, scoreInfo.score) &&
                Objects.equals(date, scoreInfo.date) &&
                Objects.equals(fileId, scoreInfo.fileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileName, score, date, fileId);
    }
}
