package com.cy.utils;

import java.util.List;

/**
 * 分页工具类
 *
 * @author CY
 */
public class PageUtil {

    private long currentPage; // 当前页
    private long pageRecordNum; // 每一页的多少条记录
    private long wholeRecord; // 总记录数

    /**
     * 构造一
     *
     * @param currentPage   当前页String
     * @param wholeRecord   总记录数
     * @param pageRecordNum 每一页的记录数
     */
    public PageUtil(int currentPage, long wholeRecord, long pageRecordNum) {
        if (currentPage <= 0) this.currentPage = 1;
        else this.currentPage = currentPage;
        this.wholeRecord = wholeRecord;
        this.pageRecordNum = pageRecordNum;
    }

    /**
     * 对列表进行分页，这种分页方式如果数据量超过了60000会有一丝丝卡顿的情况
     * 直接对List进行分页
     * 【状态：已测试2018年11月29日22:44:45】
     *
     * @param page     当前页
     * @param size     每页的条数
     * @param fileList 文件列表
     * @param num      列表中的数据的数量
     * @return 分页后的结果
     */
    public static List listPagination(int page, int size, List fileList, long num) {
        // 分页工具类
        PageUtil pageUtil = new PageUtil(page, num, size);
        // 计算显示到第几条数据
        long toIndex = 0;
        // 如果列表的数据量大于每页要显示的数据量，分页并且分页按钮出现
        if (num > pageUtil.getPageRecordNum()) toIndex = pageUtil.getFirstRecordPos() + pageUtil.getPageRecordNum();
        // 如果当前页等于最后一页，需要特殊显示，因为最后一页不一定是几条数据
        if (pageUtil.getCurrentPage() == pageUtil.getWholePage()) {
            // 计算出最后一页要显示几条数据
            long lastPageNum = num % pageUtil.getPageRecordNum();
            // 如果说计算完后等于0那么最后一页一定是10条数据
            toIndex = pageUtil.getFirstRecordPos() + (lastPageNum == 0 ? pageUtil.getPageRecordNum() : lastPageNum);
        }
        return fileList.subList((int) pageUtil.getFirstRecordPos(), (int) (toIndex));
    }

    /**
     * 得到上一页
     *
     * @return 上一页
     */
    private long getPrevPage() {
        if (this.currentPage <= 1) return 1;
        return this.currentPage - 1;
    }

    /**
     * 得到下一页
     *
     * @return 下一页
     */
    private long getNextPage() {
        if (this.currentPage >= this.getWholePage()) return this.getWholePage();
        return this.currentPage + 1;
    }

    /**
     * 得到每一页第一条记录的位置
     *
     * @return 第一条记录的位置
     */
    public long getFirstRecordPos() {
        return (this.currentPage - 1) * this.pageRecordNum;
    }

    /**
     * 得到当前页
     *
     * @return 当前页
     */
    public long getCurrentPage() {
        return currentPage;
    }

    /**
     * 得到每一页的记录数
     *
     * @return 每一页的记录数
     */
    public long getPageRecordNum() {
        return pageRecordNum;
    }

    /**
     * 获得总页数
     *
     * @return 总页数
     */
    public long getWholePage() {
        return (int) Math.ceil((this.wholeRecord / (double) this.pageRecordNum));
    }

    /**
     * 得到总记录数
     *
     * @return 总记录数
     */
    private long getWholeRecord() {
        return wholeRecord;
    }
}