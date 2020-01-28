package com.cy.dao.impl;

import com.cy.dao.IWrongTopicDao;
import com.cy.pojo.ExamPaper;
import com.cy.utils.SerializationUtil;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 错题本数据层操作类
 * By CY
 * 此类序列化的文件需要考虑位置文件名的问题，因为不同用户访问的时候文件名是不一样的
 * 使用IP来进用户特征的唯一标识
 * 已阅
 * Date 2018/11/29 12:00
 */
@Repository
public class WrongTopicDaoImpl implements IWrongTopicDao {

    // 运行环境
    private static final String userDir = System.getProperty("user.dir");

    /**
     * 序列化错题本列表到硬盘
     * 【状态：已测试2018年11月29日23:44:40】
     *
     * @param list 要保存的错题本列表
     */
    @Override
    public void saveWrongTopicListToDisc(@NotNull List<ExamPaper> list, @NotNull String ip) {
        String fileName;
        // 要操作的文件的名字
        fileName = DigestUtils.md5DigestAsHex(("UserWrongTopic" + ip).getBytes(StandardCharsets.UTF_8));
        SerializationUtil.serialize(list, userDir + File.separator + "数据" + File.separator + "错题" + File.separator + fileName + ".bin");
    }

    /**
     * 序列化本次错题列表到硬盘
     * 【状态：已测试2018年12月1日09:09:13】
     *
     * @param list 要保存的错题本列表
     */
    @Override
    public void saveOnceWrongTopicListToDisc(@NotNull List<ExamPaper> list, @NotNull String scoreId) {
        if (scoreId == null || "".equalsIgnoreCase(scoreId)) return;
        // 序列化
        SerializationUtil.serialize(list, userDir + File.separator + "数据" + File.separator + "错题" + File.separator + scoreId + ".bin");
    }

    /**
     * 反序列化错题本列表
     * 【状态：已测试2018年11月29日23:40:39】
     */
    @SuppressWarnings("all")
    @Override
    public List<ExamPaper> getWrongTopicListFromDisc(String ip) {
        String fileName = null;
        try {
            fileName = DigestUtils.md5DigestAsHex(("UserWrongTopic" + ip).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<ExamPaper> list = SerializationUtil.deserialize(userDir + File.separator + "数据" + File.separator + "错题" + File.separator + fileName + ".bin", List.class);
        if (list == null) return new ArrayList<>();
        return list;
    }

    /**
     * 从硬盘上得到本次错题本数据
     * 【状态：已测试2018年12月1日09:09:39】
     */
    @SuppressWarnings("all")
    @Override
    public List<ExamPaper> getOnceWrongTopicListFromDisc(@NotNull String scoreId) {
        if (scoreId == null || "".equalsIgnoreCase(scoreId)) return new ArrayList<>();
        List<ExamPaper> list = (List<ExamPaper>) SerializationUtil.deserialize(userDir + File.separator + "数据" + File.separator + "错题" + File.separator + scoreId + ".bin", List.class);
        if (list == null) return new ArrayList<>();
        return list;
    }

    /**
     * 添加错题到错题本中
     * 【状态：已测试2018年11月29日23:43:38】
     *
     * @param list 要添加的错题列表
     */
    @Override
    public void addWrongTopicList(List<ExamPaper> list, String ip) {
        // 得到错题本中的数据
        List<ExamPaper> wrongTopics = this.getWrongTopicListFromDisc(ip);
        list.removeAll(wrongTopics); // 求出差集
        wrongTopics.addAll(list); // 把差集添加到错题本中
        this.saveWrongTopicListToDisc(wrongTopics, ip); // 保存添加后的结果
    }

    /**
     * 从错题本中删除错题
     *
     * @param questionId 要删除的错题的Id
     */
    @Override
    public boolean deleteWrongTopic(String questionId, String ip) {
        List<ExamPaper> wrongTopics = this.getWrongTopicListFromDisc(ip); // 得到原来的错题列表
        if (wrongTopics != null) {
            ExamPaper examPaper = findObjectByQuestionId(wrongTopics, questionId); // 找到要删除的对象
            boolean isDelete = wrongTopics.remove(examPaper);// 删除了它
            this.saveWrongTopicListToDisc(wrongTopics, ip); // 保存删除后的结果
            return isDelete;
        }
        return false;
    }

    /**
     * 根据错误题目的Id找到错题的对象
     *
     * @return 错题对象
     */
    private ExamPaper findObjectByQuestionId(List<ExamPaper> list, String questionId) {
        for (ExamPaper examPaper : list) if (questionId.equals(examPaper.getQuestionId())) return examPaper;
        return null;
    }
}
