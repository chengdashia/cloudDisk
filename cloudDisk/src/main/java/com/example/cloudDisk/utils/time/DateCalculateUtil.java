package com.example.cloudDisk.utils.time;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author 成大事
 * @since 2022/4/13 21:58
 * @Description 日期工具类
 */
public class DateCalculateUtil {

    /**
     * 获取startDate日期后month月的日期
     * @param startDate 开始日期
     * @param month  几个月后
     * @return Date
     */
    public static Date getMonthDate(Date startDate, int month){
        LocalDateTime localDateTime = startDate.toInstant()
                .atZone(ZoneId.systemDefault() )
                .toLocalDateTime().plusMonths(month);
        return Date.from(localDateTime.atZone( ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取startDate日期后day天的日期
     * @param startDate 开始日期
     * @param day  几天后
     * @return  Date
     */
    public static Date getDayDate(Date startDate, int day){
        LocalDateTime localDateTime = startDate.toInstant()
                .atZone(ZoneId.systemDefault() )
                .toLocalDateTime().plusDays(day);
        return Date.from(localDateTime.atZone( ZoneId.systemDefault()).toInstant());

    }

    /**
     * 获取startDate日期后hour小时的日期
     * @param startDate 开始日期
     * @param hour  几小时后
     * @return  Date
     */
    public static Date getHourDate(Date startDate, int hour){
        LocalDateTime localDateTime = startDate.toInstant()
                .atZone(ZoneId.systemDefault() )
                .toLocalDateTime().plusHours(hour);
        return Date.from(localDateTime.atZone( ZoneId.systemDefault()).toInstant());

    }

    /**
     * 获取startDate日期后minute分钟的日期
     * @param startDate 开始日期
     * @param minute  几分钟后
     * @return  Date
     */
    public static Date getMinuteDate(Date startDate, int minute){
        LocalDateTime localDateTime = startDate.toInstant()
                .atZone(ZoneId.systemDefault() )
                .toLocalDateTime().plusMinutes(minute);
        return Date.from(localDateTime.atZone( ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取startDate日期后second秒的日期
     * @param startDate 开始日期
     * @param second  几秒后
     * @return Date
     */
    public static Date getSecondDate(Date startDate, int second){
        LocalDateTime localDateTime = startDate.toInstant()
                .atZone(ZoneId.systemDefault() )
                .toLocalDateTime().plusSeconds(second);
         return Date.from(localDateTime.atZone( ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取过期时间
     * @param date  表中的删除 创建时间
     * @return  String
     */
    public static String remainingExpirationTime(Date date){
        date = getDayDate(date, 30);
        long between = DateUtil.between(new Date(), date, DateUnit.MS);
        return DateUtil.formatBetween(between, BetweenFormatter.Level.SECOND);
    }

}
