package com.cy.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
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
}
