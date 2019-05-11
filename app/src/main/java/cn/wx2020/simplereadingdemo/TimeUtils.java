package cn.wx2020.simplereadingdemo;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    public static String getTimeDifferenceWithFormatter(DateTimeFormatter formatter, String time){

        String result = null;
        LocalDateTime nowTime = LocalDateTime.now();

        LocalDateTime publishtime = LocalDateTime.parse(time, formatter);

        int month = publishtime.getMonthValue();
        int day = publishtime.getDayOfMonth();

        Duration duration = Duration.between(publishtime, nowTime);
        Long minutes = duration.toMinutes();
        Long hours = duration.toHours();
        Long days = duration.toDays();

        if (minutes == 0)
            result = "刚刚";
        else if (minutes > 0 && minutes < 60)
            result = minutes + "分钟前";
        else if (minutes >= 60 && hours <= 23)
            result = hours + "小时前";
        else if (hours > 23 && days <= 3)
            result = days + "天前";
        else
            result = month + "月" + day + "日";
        return result;
    }

    public static String getTimeDifferenceWithPattern(String pattern, String time){

        String result = null;
        LocalDateTime nowTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime publishtime = LocalDateTime.parse(time, formatter);

        int month = publishtime.getMonthValue();
        int day = publishtime.getDayOfMonth();

        Duration duration = Duration.between(publishtime, nowTime);
        Long minutes = duration.toMinutes();
        Long hours = duration.toHours();
        Long days = duration.toDays();

        if (minutes == 0)
            result = "刚刚";
        else if (minutes > 0 && minutes < 60)
            result = minutes + "分钟前";
        else if (minutes >= 60 && hours <= 23)
            result = hours + "小时前";
        else if (hours > 23 && days <= 3)
            result = days + "天前";
        else
            result = month + "月" + day + "日";
        return result;
    }

    public static String getTimeDifferenceFromUnixTimeWithPattern(String pattern, Long time){

        String result = null;
        LocalDateTime nowTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        String intime = formatter.format(LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.systemDefault()));

        LocalDateTime publishtime = LocalDateTime.parse(intime, formatter);

        int month = publishtime.getMonthValue();
        int day = publishtime.getDayOfMonth();

        Duration duration = Duration.between(publishtime, nowTime);
        Long minutes = duration.toMinutes();
        Long hours = duration.toHours();
        Long days = duration.toDays();

        if (minutes == 0)
            result = "刚刚";
        else if (minutes > 0 && minutes < 60)
            result = minutes + "分钟前";
        else if (minutes >= 60 && hours <= 23)
            result = hours + "小时前";
        else if (hours > 23 && days <= 3)
            result = days + "天前";
        else
            result = month + "月" + day + "日";
        return result;
    }

}
