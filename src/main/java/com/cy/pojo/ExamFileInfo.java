package com.cy.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 记录用户题库目录下面导入的Excel文件的位置
 * 因为文件可能不在同一个文件夹中所以这个实体类很有必要
 * 【状态：已测试2018年11月27日22:59:46】
 * <p>
 * By CY
 * Date 2018/11/27 13:17
 */

@Data
@ApiModel(description = "一条试卷文件的信息")
public class ExamFileInfo implements Serializable {

    @ApiModelProperty(value = "文件随机生成的Id，以后还会根据这个Id找到这个文件的", name = "fileId", required = true, example = "6e8c6dac9379437b8890f0a658fe8292")
    private String fileId;

    @ApiModelProperty(value = "文件的真实的名字", name = "fileName", required = true, example = "CoreJava-01")
    private String fileName;

    @JsonIgnore // 这个属性不让前台看到
    @ApiModelProperty(value = "文件的全路径，用来链接这个文件", name = "realPath", required = true, example = "D:/CoreJava/CoreJava-01.xlsx")
    private String realPath;
}
