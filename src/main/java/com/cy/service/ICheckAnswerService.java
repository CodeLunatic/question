package com.cy.service;

import com.cy.pojo.ExerciseInfo;

import java.util.List;

/**
 * 此类用来检查试卷的答案
 * 【状态：已测试2018年12月1日09:33:07】
 * By CY
 * Date 2018/11/29 16:13
 */
public interface ICheckAnswerService {


    /**
     * 检查答案的方法
     * 【状态：已测试2018年12月1日09:32:04】
     *
     * @param exerciseInfos 用户的答卷信息
     * @param fileId        试卷Id
     * @param ip            用户的唯一标识
     */
    void checkAnswer(List<ExerciseInfo> exerciseInfos, String fileId, String ip);

}
