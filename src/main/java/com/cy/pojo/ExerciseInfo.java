package com.cy.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户的选择答案的实体类，用于做批卷操作
 * 【状态：已测试2018年12月1日09:17:31】
 * By CY
 * Date 2018/11/29 13:55
 */
@Data
@ApiModel(description = "用户答案")
public class ExerciseInfo implements Serializable {

    @JsonProperty("a")
    @ApiModelProperty(value = "用户的答案", name = "answer", required = true, example = "A")
    private String answer;

    @JsonProperty("q")
    @ApiModelProperty(value = "题目的Id", name = "questionId", required = true, example = "6e8c6dac9379437b8890f0a658fe8292")
    private String questionId;

    @JsonProperty("t")
    @ApiModelProperty(value = "题目的类型", name = "type", required = true, example = "单选题")
    private String type;
}
