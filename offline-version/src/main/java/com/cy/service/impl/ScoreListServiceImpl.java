package com.cy.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Font;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.TableStyle;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cy.dao.IScoreListDao;
import com.cy.pojo.ScoreInfo;
import com.cy.service.IScoreListService;
import com.cy.utils.PageUtil;
import entity.PageResult;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分数列表服务层
 * 【状态：已测试2018年12月1日09:29:08】
 * By CY
 * Date 2018/11/29 18:14
 */
@Service
public class ScoreListServiceImpl implements IScoreListService {

    @Resource
    private IScoreListDao scoreListDao;

    /**
     * 进行分数列表的分页操作
     * 【状态：已测试2018年12月1日09:31:24】
     *
     * @param date 根据日期来进行查询
     * @param page 当前页
     * @param size 每页有多少条数据
     * @param ip   用户的唯一标识
     * @return 返回分页的结果
     */
    @Override
    public PageResult findPage(Date date, int page, int size, String ip) {
        // 查询出分数列表
        List<ScoreInfo> list = scoreListDao.findScoreByDate(date, ip);
        // 对分数列表进行排序
        list.sort((o1, o2) -> {
            long time = o1.getDate().getTime();
            long time1 = o2.getDate().getTime();
            return (int) (time1 - time);
        });
        // 分数列表的大小
        int num = list.size();
        // 返回分页结果
        return new PageResult(num, PageUtil.listPagination(page, size, list, num));
    }

    /**
     * 删除一条成绩记录
     * 【状态：已测试2018年12月1日09:31:33】
     *
     * @param id 成绩的Id
     * @param ip 用户的唯一标识
     * @return 是否删除成功
     */
    @Override
    public boolean delete(String id, String ip) {
        return scoreListDao.deleteScoreById(id, ip);
    }

    /**
     * 清空分数列表
     * 【状态：已测试2018年12月1日09:31:41】
     *
     * @param ip 用户的唯一标识
     * @return 是否已经成功
     */
    @Override
    public boolean deleteAll(String ip) {
        return scoreListDao.deleteAll(ip);
    }

    /**
     * 导出Excel
     *
     * @param outputStream 输出流
     * @param ip           用户的唯一标识
     */
    @Override
    public void exportExcel(OutputStream outputStream, String ip) {
        List<ScoreInfo> list = scoreListDao.getScoreInfoFromDisc(ip);
        BufferedOutputStream out = new BufferedOutputStream(outputStream);
        try {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            //写第一个sheet
            Sheet sheet = new Sheet(1, 0, ScoreInfo.class);
            sheet.setSheetName("分数信息列表");
            TableStyle tableStyle = setTableStyle();
            sheet.setTableStyle(tableStyle);
            sheet.setColumnWidthMap(getColumnWidthMap());
            writer.write(list, sheet);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置Excel的列宽
     *
     * @return 列宽设置
     */
    private Map<Integer, Integer> getColumnWidthMap() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 13200);
        map.put(1, 4500);
        map.put(2, 9900);
        return map;
    }

    /**
     * 设置Excel的样式
     *
     * @return TableStyle对象
     */
    private TableStyle setTableStyle() {
        // 列表样式
        TableStyle tableStyle = new TableStyle();
        // 设置表头样式
        Font tableHeadFont = new Font();
        tableHeadFont.setFontName("Microsoft YaHei"); //字体名称
        tableHeadFont.setFontHeightInPoints((short) 12); // 字体的大小
        tableHeadFont.setBold(true); // 加粗
        tableStyle.setTableHeadFont(tableHeadFont);
        // 设置内容样式
        Font tableContentFont = new Font(); //字体
        tableContentFont.setFontName("Microsoft YaHei"); //字体名称
        tableContentFont.setFontHeightInPoints((short) 10); //字体大小
        tableStyle.setTableContentFont(tableContentFont); // 表中的内容样式
        tableStyle.setTableContentBackGroundColor(IndexedColors.WHITE); // 内容的背景
        return tableStyle;
    }
}
