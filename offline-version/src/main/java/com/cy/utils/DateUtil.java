package com.cy.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 得到今日的开始时间对象
     *
     * @return 凌晨的时间对象
     */
    public static Date getTodayStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 得到今日的结束时间对象
     *
     * @return 凌晨的时间对象
     */
    public static Date getTodayEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 得到某一天的开始时间
     *
     * @param oneDay 某一天
     * @return 某一天的开始时间
     */
    public static Date getOneDayStartTime(Date oneDay) {
        Calendar oneDayStart = Calendar.getInstance();
        oneDayStart.setTime(oneDay);
        oneDayStart.set(Calendar.HOUR_OF_DAY, 0);
        oneDayStart.set(Calendar.MINUTE, 0);
        oneDayStart.set(Calendar.SECOND, 0);
        oneDayStart.set(Calendar.MILLISECOND, 0);
        return oneDayStart.getTime();
    }

    /**
     * 得到某一天的结束时间
     *
     * @return 某一天的结束时间
     */
    public static Date getOneDayEndTime(Date oneDay) {
        Calendar oneDayEnd = Calendar.getInstance();
        oneDayEnd.setTime(oneDay);
        oneDayEnd.set(Calendar.HOUR_OF_DAY, 23);
        oneDayEnd.set(Calendar.MINUTE, 59);
        oneDayEnd.set(Calendar.SECOND, 59);
        oneDayEnd.set(Calendar.MILLISECOND, 999);
        return oneDayEnd.getTime();
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间字符串
     */
    public static String getDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }
}
