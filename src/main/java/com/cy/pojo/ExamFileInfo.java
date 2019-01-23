package com.cy.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * 记录用户题库目录下面导入的Excel文件的位置
 * 因为文件可能不在同一个文件夹中所以这个实体类很有必要
 * 【状态：已测试2018年11月27日22:59:46】
 * <p>
 * By CY
 * Date 2018/11/27 13:17
 */
public class ExamFileInfo implements Serializable {

    // 文件随机生成的Id，以后还会根据这个Id找到这个文件的
    private String fileId;
    // 文件的真实的名字
    private String fileName;
    // 文件的全路径，用来链接这个文件
    @JsonIgnore // 这个属性不让前台看到
    private String realPath;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }
}
