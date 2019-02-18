package com.cy.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 分数列表实体类
 * 【状态：已测试2018年12月1日09:17:21】
 * By CY
 * Date 2018/11/29 17:04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户一条的分数信息")
@JsonIgnoreProperties(value = "cellStyleMap")
public class ScoreInfo extends BaseRowModel implements Serializable {

    @ApiModelProperty(value = "分数的Id", name = "id", required = true, example = "6e8c6dac9379437b8890f0a658fe8292")
    private String id;

    @ExcelProperty(value = "试卷名称", index = 0)
    @ApiModelProperty(value = "试卷的名称", name = "fileName", required = true, example = "CoreJava-01")
    private String fileName;

    @ExcelProperty(value = "试卷分数", index = 1)
    @ApiModelProperty(value = "试卷的分数", name = "score", required = true, example = "100")
    private String score;

    @ExcelProperty(value = "提交时间", index = 2, format = "yyyy年MM月dd日 HH点mm分ss秒")
    @ApiModelProperty(value = "提交的日期", name = "date", required = true, example = "2018年12月12日 12点12分12秒")
    private Date date;

    @ApiModelProperty(value = "文件的Id", name = "fileId", required = true, example = "6e8c6dac9379437b8890f0a658fe8292")
    private String fileId;
}
