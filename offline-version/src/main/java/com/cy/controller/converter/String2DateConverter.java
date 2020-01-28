package com.cy.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 一个SpringMVC的String到Date类型的转换器
 * 【状态：已测试成功2018年12月1日08:07:14】
 * By CY
 * Date 2018/11/30 10:27
 */
@Component
public class String2DateConverter implements Converter<String, Date> {

    /**
     * 针对不同的格式进行转换
     *
     * @param s 用户传入的String字符串
     * @return 返回处理结果给后台
     */
    @Override
    public Date convert(String s) {
        try {
            if (s.matches("\\d{4}-\\d{2}-\\d{2}"))
                return new SimpleDateFormat("yyyy-MM-dd").parse(s);
            if (s.matches("\\d{4}/\\d{2}/\\d{2}"))
                return new SimpleDateFormat("yyyy/MM/dd").parse(s);
            if (s.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"))
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
            if (s.matches("\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}"))
                return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(s);
            if (s.matches("\\d+"))
                return new Date(Long.parseLong(s));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 如果没有任何的匹配，返回null给后台继续处理
        return null;
    }
}
