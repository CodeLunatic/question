package com.cy.service.impl;

import com.cy.dao.IExamFileListDao;
import com.cy.dao.IExamPaperDao;
import com.cy.dao.IWrongTopicDao;
import com.cy.pojo.ExamFileInfo;
import com.cy.pojo.ExamPaper;
import com.cy.service.IExamPaperService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 试卷操作的服务层的实现类
 * 【状态：已测试2018年12月1日09:28:18】
 * By CY
 * Date 2018/11/26 23:51
 */
@Service
public class ExamPaperServiceImpl implements IExamPaperService {

    @Resource
    private IExamPaperDao examPaperDao;

    @Resource
    private IExamFileListDao fileListDao;

    @Resource
    private IWrongTopicDao wrongTopicDao;

    /**
     * 读入获取整份试卷
     * 【状态：已测试2018年11月27日22:58:43】
     *
     * @throws IOException 如果读取文件或者路径什么的有问题的话
     */
    @Override
    @Cacheable(value = "examPaper", key = "#fileId")
    public List<ExamPaper> getExamPaper(String fileId) throws IOException {
        // 通过文件的Id得到每个文件的实际的路径
        ExamFileInfo fileInfoByFileId = fileListDao.getFileInfoByFileId(fileId);
        String realPath = null;
        if (fileInfoByFileId != null) realPath = fileInfoByFileId.getRealPath();
        // 获取试题的内容
        return examPaperDao.getExamPaperList(realPath);
    }

    /**
     * 得到整个错题本数据
     * 【状态：已测试2018年11月30日00:34:28】
     *
     * @return 错题本
     */
    @Override
    @Cacheable(value = "wrongTopicList", key = "#ip")
    public List<ExamPaper> getWrongTopicList(String ip) {
        return wrongTopicDao.getWrongTopicListFromDisc(ip);
    }

    /**
     * 根据题目的Id来删除错题本上的一道题
     * 【状态：已测试2018年12月1日09:28:36】
     *
     * @param questionId 题目的Id
     * @return 是否删除成功
     */
    @Override
    @CacheEvict(value = "wrongTopicList", key = "#ip")
    public boolean deleteWrongTopicByQuestionId(String questionId, String ip) {
        return wrongTopicDao.deleteWrongTopic(questionId, ip);
    }

    /**
     * 得到分数对应的错题
     * 【状态：已测试2018年12月1日09:28:45】
     *
     * @param scoreId 分数的Id
     * @return 分数对应的错题
     */
    @Override
    @Cacheable(value = "onceWrongTopicList", key = "#scoreId")
    public List<ExamPaper> getOnceWrongTopicList(String scoreId) {
        return wrongTopicDao.getOnceWrongTopicListFromDisc(scoreId);
    }
}
