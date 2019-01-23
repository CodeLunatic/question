package com.cy.dao;

import com.cy.pojo.ScoreInfo;

import java.util.Date;
import java.util.List;

/**
 * By CY
 * Date 2018/11/29 17:23
 * 已阅
 */
public interface IScoreListDao {

    /**
     * 根据日期来查询分数列表
     *
     * @param date 日期
     * @return 分数列表
     */
    List<ScoreInfo> findScoreByDate(Date date, String ip);

    /**
     * 保存分数到硬盘中
     *
     * @param scoreInfo 分数信息
     */
    void addScoreToDisc(ScoreInfo scoreInfo, String ip);

    /**
     * 通过Id删除分数
     *
     * @param id 分数的Id
     */
    boolean deleteScoreById(String id, String ip);

    /**
     * 清空分数列表
     *
     * @return 返回是否清空
     */
    boolean deleteAll(String ip);

    /**
     * 获取所有的分数信息
     *
     * @param ip 用户的唯一标识
     * @return 分数信息列表
     */
    List<ScoreInfo> getScoreInfoFromDisc(String ip);
}
