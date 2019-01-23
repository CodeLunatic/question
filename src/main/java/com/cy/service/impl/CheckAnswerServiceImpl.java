package com.cy.service.impl;

import com.cy.dao.IExamFileListDao;
import com.cy.dao.IExamPaperDao;
import com.cy.dao.IScoreListDao;
import com.cy.dao.IWrongTopicDao;
import com.cy.pojo.ExamFileInfo;
import com.cy.pojo.ExamPaper;
import com.cy.pojo.ExerciseInfo;
import com.cy.pojo.ScoreInfo;
import com.cy.service.ICheckAnswerService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 批卷操作的业务
 * 【状态：已测试2018年12月1日09:18:26】
 * By CY
 * Date 2018/11/29 16:14
 */
@Service
public class CheckAnswerServiceImpl implements ICheckAnswerService {

    @Resource
    private IExamPaperDao examPaperDao;

    @Resource
    private IWrongTopicDao wrongTopicDao;

    @Resource
    private IScoreListDao scoreListDao;

    @Resource
    private IExamFileListDao examFileListDao;

    /**
     * 在进行批卷的时候，同时要进行分数的记录和错题本的操作!
     * 【状态：已测试2018年12月1日09:18:43】
     *
     * @param exerciseInfos 用户的答题的信息
     * @param fileId        属性字段
     */
    @Override
    @CacheEvict(value = "wrongTopicList", key = "#ip")
    public void checkAnswer(List<ExerciseInfo> exerciseInfos, String fileId, String ip) {
        // 满分100
        double wholeScore = 100;
        // 获取硬盘上的答案
        List<ExamPaper> answers = examPaperDao.getExamPaperListFromDisc(fileId);
        // 获取每道题的分数
        double score = getOnceScore(answers);
        // 保存用户的错题的集合
        ArrayList<ExamPaper> errors = new ArrayList<>();
        // 开始批卷
        for (ExamPaper answer : answers) { // 遍历答案
            if (answer.getType() == null || answer.getQuestionId() == null || answer.getAnswer() == null) continue;
            if (!(answer.getType().contains("选") || answer.getType().contains("断")))
                continue;
            // 获取用户的答案
            String answerById = getUserAnswerById(answer.getQuestionId(), exerciseInfos);
            // 如果用户没做
            if (answerById == null) {
                answer.setMyAnswer("未答题");
                errors.add(answer);
                wholeScore -= score;
            } else if (!sortAlphabetAnswer(answer.getAnswer()).equalsIgnoreCase(sortAlphabetAnswer(answerById))) {// 如果答案不正确
                answer.setMyAnswer(answerById);
                errors.add(answer);
                wholeScore -= score;
            }
        }
        // 批卷操作后
        afterCheckAnswer(fileId, ip, wholeScore, errors);
    }

    /**
     * 批卷操作后要进行的操作
     * 【状态：已测试2018年12月1日09:24:38】
     *
     * @param fileId     文件的Id
     * @param ip         用户的唯一标识
     * @param wholeScore 用户的分数
     * @param errors     用户的错题列表
     */
    private void afterCheckAnswer(String fileId, String ip, double wholeScore, ArrayList<ExamPaper> errors) {
        // 生成分数的Id
        String scoreId = UUID.randomUUID().toString().replaceAll("-", "");
        // 保存本次错题
        wrongTopicDao.saveOnceWrongTopicListToDisc(errors, scoreId);
        // 追加错题本
        wrongTopicDao.addWrongTopicList(errors, ip);
        // 添加分数到分数列表中
        addScoreToList(fileId, ip, wholeScore, scoreId);
    }

    /**
     * 添加分数到分数列表
     * 【状态：已测试2018年12月1日09:24:29】
     *
     * @param fileId     文件的Id
     * @param ip         用户的唯一标识
     * @param wholeScore 用户的分数
     * @param scoreId    分数的Id
     */
    private void addScoreToList(String fileId, String ip, double wholeScore, String scoreId) {
        ScoreInfo scoreInfo = new ScoreInfo();
        scoreInfo.setFileId(fileId);
        scoreInfo.setDate(new Date()); // 提交时间
        ExamFileInfo examFileInfo = examFileListDao.getFileInfoByFileId(fileId);
        if (examFileInfo == null) throw new NullPointerException("文件已经不在了!");
        scoreInfo.setFileName(examFileInfo.getFileName()); // 文件名
        scoreInfo.setId(scoreId); // 分数的Id
        scoreInfo.setScore(formatScoreNumber(wholeScore)); // 设置分数
        scoreListDao.addScoreToDisc(scoreInfo, ip); // 添加到分数列表中，并重新序列化到硬盘
    }

    /**
     * 格式化分数
     * 【状态：已测试2018年11月29日23:56:17】
     *
     * @param score 分数
     * @return 格式化后的结果
     */
    private String formatScoreNumber(double score) {
        if (score < 0) score = -score;
        return new DecimalFormat("#.##").format(score);
    }

    /**
     * 获取没到题的分数
     * 【状态：已测试2018年11月29日23:24:57】
     *
     * @param answers 答案列表
     * @return 每道题的分数
     */
    private double getOnceScore(List<ExamPaper> answers) {
        int count = 0;
        for (ExamPaper answer : answers) {
            if (answer.getType() == null) continue;
            if (answer.getType().contains("选") || answer.getType().contains("断"))
                count += 1;
        }
        return 100.0 / count;
    }

    /**
     * 根据Id获取用户的答案
     * 【状态：已测试2018年11月29日23:27:40】
     *
     * @return 用户的答案
     */
    private String getUserAnswerById(String quetsionId, List<ExerciseInfo> list) {
        for (ExerciseInfo exerciseInfo : list) {
            if (exerciseInfo.getQuestionId() == null || exerciseInfo.getAnswer() == null) continue;
            if (exerciseInfo.getQuestionId().equals(quetsionId)) return exerciseInfo.getAnswer();
        }
        return null;
    }

    /**
     * 对字母进行排序
     * 【2018年11月29日23:28:59已测试】
     *
     * @param string 要排序的字母
     * @return 排序的结果
     */
    private String sortAlphabetAnswer(String string) {
        // 判断字符串是不是空
        if (string == null) return "";
        // 判断字符串是不是字母
        if (!string.matches("^[a-fA-f]+$")) return string;
        // 拆分字符串
        String[] split = string.split("");
        // 排序
        Arrays.sort(split);
        // 返回排序后的结果
        return StringUtils.arrayToDelimitedString(split, "").toUpperCase();
    }
}
