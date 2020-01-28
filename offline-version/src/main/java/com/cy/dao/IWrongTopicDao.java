package com.cy.dao;

import com.cy.pojo.ExamPaper;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 错题本数据层
 * By CY
 * 已阅
 * Date 2018/11/29 11:57
 */
public interface IWrongTopicDao {

    /**
     * 保存错题数据到硬盘中，仅用于本次错题
     *
     * @param list 要保存的错题本列表
     */
    void saveWrongTopicListToDisc(List<ExamPaper> list, String ip);

    /**
     * 从硬盘上得到错题本数据
     * 【状态：已测试2018年11月30日00:34:52】
     */
    List<ExamPaper> getWrongTopicListFromDisc(String ip);

    /**
     * 追加错题到错题本中
     *
     * @param list 要添加的错题列表
     */
    void addWrongTopicList(List<ExamPaper> list, String ip);

    /**
     * 从错题本中删除一条错题数据
     *
     * @param questionId 要删除的错题的Id
     */
    boolean deleteWrongTopic(String questionId, String ip);

    /**
     * 序列化本次错题列表到硬盘
     * 【状态：已测试2018年11月29日23:37:59】
     *
     * @param list 要保存的错题本列表
     */
    void saveOnceWrongTopicListToDisc(@NotNull List<ExamPaper> list, @NotNull String scoreId);

    /**
     * 从硬盘上得到本次错题本数据
     */
    List<ExamPaper> getOnceWrongTopicListFromDisc(@NotNull String scoreId);
}
