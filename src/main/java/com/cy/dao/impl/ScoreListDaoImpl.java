package com.cy.dao.impl;

import com.cy.dao.IScoreListDao;
import com.cy.pojo.ScoreInfo;
import com.cy.utils.DateUtil;
import com.cy.utils.SerializationUtil;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 分数列表操作的数据层
 * 【状态：已测试2018年12月1日09:07:09】
 * By CY
 * Date 2018/11/29 17:32
 */
@Repository
public class ScoreListDaoImpl implements IScoreListDao {

    // 运行环境
    private static final String userDir = System.getProperty("user.dir");

    /**
     * 根据日期查找分数
     * 【状态：已测试2018年11月30日10:59:51】
     *
     * @param date 日期
     * @param ip   用户的唯一标识
     * @return 分数列表
     */
    @Override
    public List<ScoreInfo> findScoreByDate(Date date, String ip) {
        // 读取分数信息
        List<ScoreInfo> list = getScoreInfoFromDisc(ip);
        // 新建查询结果
        ArrayList<ScoreInfo> result = new ArrayList<>();
        if (date != null) { // 查询
            long oneDayStartTime = DateUtil.getOneDayStartTime(date).getTime(); // 查询的起始
            long oneDayEndTime = DateUtil.getOneDayEndTime(date).getTime(); // 查询的终止
            for (ScoreInfo scoreInfo : list) {
                Date scoreDate = scoreInfo.getDate(); // 分数的日期
                // 如果匹配
                if (oneDayStartTime <= scoreDate.getTime() && scoreDate.getTime() <= oneDayEndTime)
                    result.add(scoreInfo);
            }
            return result;
        }
        return list;
    }

    /**
     * 从硬盘上获取列表
     * 【状态：已测试2018年11月29日23:57:20】
     *
     * @param ip 用户的唯一标识
     * @return 返回硬盘上的列表
     */
    @SuppressWarnings("all")
    @Override
    public List<ScoreInfo> getScoreInfoFromDisc(String ip) {
        String fileName = null;
        try {
            fileName = DigestUtils.md5DigestAsHex(("ScoreList" + ip).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<ScoreInfo> list = SerializationUtil.deserialize(userDir + File.separator + "数据" + File.separator + "临时" + File.separator + fileName + ".bin", List.class);
        if (list == null) return new ArrayList<>();
        return list;
    }

    /**
     * 添加分数信息到列表中
     *
     * @param scoreInfo 分数信息
     * @param ip        用户的唯一标识
     */
    @Override
    public void addScoreToDisc(ScoreInfo scoreInfo, String ip) {
        // 从硬盘上获取分数列表
        List<ScoreInfo> scoreInfoFromDisc = getScoreInfoFromDisc(ip);
        // 添加一条新纪录
        scoreInfoFromDisc.add(scoreInfo);
        // 保存到硬盘
        saveScoreInfoToDisc(scoreInfoFromDisc, ip);
    }

    /**
     * 保存分数列表到硬盘
     * 【状态：已测试2018年11月29日23:58:33】
     *
     * @param scoreInfoFromDisc 分数列表
     * @param ip                用户的唯一标识
     */
    private void saveScoreInfoToDisc(List<ScoreInfo> scoreInfoFromDisc, String ip) {
        String fileName;
        fileName = DigestUtils.md5DigestAsHex(("ScoreList" + ip).getBytes(StandardCharsets.UTF_8));
        SerializationUtil.serialize(scoreInfoFromDisc, userDir + File.separator + "数据" + File.separator + "临时" + File.separator + fileName + ".bin");
    }

    /**
     * 根据Id删除分数数据
     *
     * @param id 分数的Id
     * @param ip 用户的唯一标识
     * @return 删除是否成功
     */
    @SuppressWarnings("all")
    @Override
    public boolean deleteScoreById(String id, String ip) {
        if (id == null || "".equals(id)) return false;
        List<ScoreInfo> scoreInfoFromDisc = getScoreInfoFromDisc(ip);
        ScoreInfo scoreInfoById = getScoreInfoById(scoreInfoFromDisc, id);
        boolean flag = scoreInfoFromDisc.remove(scoreInfoById);
        // 从硬盘上移除错题
        new File(userDir + File.separator + "数据" + File.separator + "错题" + File.separator + id + ".bin").delete();
        if (flag) saveScoreInfoToDisc(scoreInfoFromDisc, ip);
        return flag;
    }

    /**
     * 清空数据
     *
     * @param ip 用户的唯一标识
     * @return 返回是否清空成功
     */
    @SuppressWarnings("all")
    @Override
    public boolean deleteAll(String ip) {
        String fileName = null;
        try {
            fileName = DigestUtils.md5DigestAsHex(("ScoreList" + ip).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<ScoreInfo> scoreInfoFromDisc = getScoreInfoFromDisc(ip);
        // 将错题列表中硬盘上删除
        for (ScoreInfo scoreInfo : scoreInfoFromDisc) {
            new File(userDir + File.separator + "数据" + File.separator + "错题" + File.separator + scoreInfo.getId() + ".bin").delete();
        }
        return new File(userDir + File.separator + "数据" + File.separator + "临时" + File.separator + fileName + ".bin").delete();
    }

    /**
     * 通过Id获取分数信息
     *
     * @param list 要查询的列表
     * @param id   要查询的Id
     * @return 返回查询到的对象
     */
    private ScoreInfo getScoreInfoById(@NotNull List<ScoreInfo> list, @NotNull String id) {
        for (ScoreInfo scoreInfo : list) if (id.equals(scoreInfo.getId())) return scoreInfo;
        return null;
    }
}
