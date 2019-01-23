package com.cy.service.impl;

import com.cy.dao.IExamFileListDao;
import com.cy.pojo.ExamFileInfo;
import com.cy.service.IExamFileListService;
import com.cy.utils.PageUtil;
import entity.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文件列表服务的实现类
 * 【状态：已测试2018年12月1日09:27:36】
 * By CY
 * Date 2018/11/28 18:27
 */
@Service
public class ExamFileListServiceImpl implements IExamFileListService {

    @Resource
    private IExamFileListDao fileListDao;

    /**
     * 对文件列表中的数据进行模糊查询和分页
     * 【状态：已测试2018年11月29日22:39:09】
     *
     * @param fileName 文件的名字，需要模糊查询
     * @param page     当前页
     * @param size     每页的数据量
     * @return 返回分页的结果
     */
    @SuppressWarnings("all")
    @Override
    public PageResult getFileList(String fileName, int page, int size) {
        // 文件列表
        List<ExamFileInfo> fileList = fileListDao.queryListByFileName(fileName);
        // 列表数据量
        long num = fileList.size();
        // 对列表进行分页
        List<ExamFileInfo> examFileInfos = PageUtil.listPagination(page, size, fileList, num);
        // 分页数据
        return new PageResult(num, examFileInfos);
    }
}
