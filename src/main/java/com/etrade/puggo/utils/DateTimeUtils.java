package com.etrade.puggo.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description 时间日期工具类
 * @Author liuyuqing
 * @Date 2020/10/30 10:21
 **/
public class DateTimeUtils {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_DIGITAL_PATTERN = "yyyyMMddHHmmss";
    public static final String MINUTE_PATTERN = "yy-MM-dd HH:mm";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_PATTERN_DAY = "yyyyMMdd";
    public static final String DATE_TIME_PATTERN_CHINESE_YEAR = "yyyy年MM月dd日";
    public static final String DATE_TIME_PATTERN_CHINESE_CALENDAR = "MM月dd日";
    public static final String DATE_TIME_PATTERN_CHINESE_V1 = "MM月dd日HH:mm";
    public static final String DATE_TIME_PATTERN_CHINESE_V2 = "MM月dd日 HH:mm";
    public static final String DATE_TIME_PATTERN_CHINESE_V3 = "MM月dd日HH:mm";
    public static final String DATE_YEAR = "yyyy";
    public static final String DATE_DAY = "MM-dd";
    public static final String DATE_DAY2 = "M.d";
    public static final String DATE_TIME = "HH:mm";

    private static final String DEFAULT_ZONE = "UTC";

    
    /**
     * 获取当前时间 (UTC)
     */
    public static LocalDateTime now() {
        return LocalDateTime.now(ZoneId.of(DEFAULT_ZONE));
    }

    /**
     * 获取当前日期 (UTC)
     */
    public static LocalDate dateNow() {
        return LocalDate.now(ZoneId.of(DEFAULT_ZONE));
    }

    /**
     * 获取今日日期
     */
    public static LocalDate nowDate() {
        return LocalDate.now();
    }

    /**
     * 获取昨日日期
     */
    public static LocalDate yesterday() {
        return nowDate().minusDays(1);
    }

    /**
     * 获取此刻时间戳（毫秒）
     */
    public static Long getNowMillSecondTimeStamp() {
        return localDateTimeToTimeStamp(now());
    }

    /**
     * 获取当前时间戳（秒）
     */
    public static Long getNowSecondTimeStamp() {
        return Instant.now().getEpochSecond();
    }

    /**
     * LocalDateTime转为时间戳
     */
    public static Long localDateTimeToTimeStamp(LocalDateTime datetime) {
        if (datetime == null) {
            return null;
        }
        return datetime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * date转LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        if (null == date) {
            return null;
        }
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }


    /**
     * 字符串日期转LocalDateTime
     */
    public static LocalDateTime stringDateToLocalDateTime(String value) {
        if (null == value) {
            return null;
        }
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 获取当天的最晚日期时间
     */
    public static LocalDateTime getEndOfDay(String value) {
        if (null == value) {
            return null;
        }
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN))
            .with(LocalTime.MAX);
    }

    /**
     * 获取当天的最早日期时间
     */
    public static LocalDateTime getStartOfDay(String value) {
        if (null == value) {
            return null;
        }
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN))
            .with(LocalTime.MIN);
    }

    public static LocalDateTime getStartOfDay(LocalDateTime value) {
        if (null == value) {
            return null;
        }
        return value.with(LocalTime.MIN);
    }

    public static LocalDateTime getEndOfDay(LocalDateTime value) {
        if (null == value) {
            return null;
        }
        return value.with(LocalTime.MAX);
    }


    public static LocalDateTime getStartOfToDay() {
        return now().with(LocalTime.MIN);
    }


    /**
     * 获取当月的最早日期时间
     */
    public static LocalDateTime startOfMonth() {
        LocalDateTime firstDayThisMonth = now().withDayOfMonth(1);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDateTime
            .parse(df.format(firstDayThisMonth) + " 00:00:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 字符串日期转LocalDate
     */
    public static LocalDate stringDateToLocalDate(String value) {
        if (null == value) {
            return null;
        }
        return LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * 获取两个LocalDateTime中的较小值
     */
    public static LocalDateTime minTime(LocalDateTime time1, LocalDateTime time2) {
        if (time1 == null) {
            return time2;
        }
        if (time2 == null) {
            return time1;
        }
        return time2.compareTo(time1) > 0 ? time1 : time2;
    }

    /**
     * 获取当前日期的字符串形式
     */
    public static String formattedNow() {
        return formatted(now());
    }

    /**
     * 将LocalDateTime转为字符串日期
     */
    public static String formatted(LocalDateTime time) {
        if (time == null) {
            return null;
        }
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(time);
    }

    /**
     * 将LocalDate转为字符串日期
     */
    public static String formatted(LocalDate date) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(date);
    }

    /**
     * 时间戳(秒)转localDataTime
     */
    public static LocalDateTime timestampSecondToLocalDateTime(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.of(DEFAULT_ZONE));
    }

    public static long localDateToTimestampSecond(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneId.of(DEFAULT_ZONE)).toInstant().toEpochMilli() / 1000;
    }

    public static long localDateToTimestamp(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneId.of(DEFAULT_ZONE)).toInstant().toEpochMilli();
    }

    /**
     * 获取两个日期间隔的天数
     */
    public static long betweenDay(LocalDate date1, LocalDate date2) {
        return ChronoUnit.DAYS.between(date1, date2);
    }

    /**
     * 获取指定日期当天的结束时间 例：返回2019-04-08 23:59:59.999999
     *
     * @param date 指定日期
     * @return 当天结束时间
     */
    public static Date endOfDay(Date date) {
        LocalDateTime localDateTime =
            LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime endTime = localDateTime.with(LocalTime.MAX);
        return Date.from(endTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取指定日期当天的结束时间 例：返回2019-04-08 23:59:59.999999
     *
     * @param date 指定日期
     * @return 当天结束时间
     */
    public static LocalDateTime endOfDay(LocalDateTime date) {
        return date.with(LocalTime.MAX);
    }


    /**
     * 获取指定日期当天的开始时间
     *
     * @param date 指定日期
     * @return 当天结束时间
     */
    public static LocalDateTime startOfDay(LocalDateTime date) {
        return date.with(LocalTime.MIN);
    }


    /**
     * 获取指定日期当天的开始时间 例：返回2019-04-08 00:00
     *
     * @param date 指定日期
     * @return 当天开始时间
     */
    public static Date startOfDay(Date date) {
        LocalDateTime localDateTime =
            LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime endTime = localDateTime.with(LocalTime.MIN);
        return Date.from(endTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取第N天后的日期时间
     */
    public static LocalDateTime getPlusDays(long n) {
        return now().plusDays(Long.valueOf(n));
    }

    /**
     * 时间戳转localDate
     */
    public static LocalDate timestampToLocalDate(Long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of(DEFAULT_ZONE));
        return localDateTime.toLocalDate();
    }

    /**
     * 年龄
     */
    public static int getCurrentAge(String birthday) {
        LocalDate today = LocalDate.now();
        LocalDate playerDate = LocalDate
            .from(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(birthday));
        return (int) ChronoUnit.YEARS.between(playerDate, today);
    }

    /**
     * 月份
     */
    public static int getMonth(String birthday) {
        LocalDate birthDate = stringDateToLocalDate(birthday);
        return birthDate.getMonthValue();
    }

    /**
     * 获取历史时间
     *
     * @param days 天数
     * @return 返回days天前的历史时间
     */
    public static LocalDateTime getHistoryDateTime(int days) {
        return now().minusDays(days);
    }

    /**
     * 比较两个时间大小
     *
     * @param time1 时间1
     * @param time2 时间2
     * @return 比较结果：0-相同，1-time1大，2-time2大
     */
    public static int compareTime(LocalDateTime time1, LocalDateTime time2) {
        return localDateTimeToTimeStamp(time1).compareTo(localDateTimeToTimeStamp(time2));
    }

    //往后推迟n小时
    public static Date getHoursAfter(Integer n, Date time) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) + n);
        return c.getTime();
    }

    /**
     * localDateTime 转 自定义格式string
     *
     * @param localDateTime
     * @param format        推荐使用类体中定义的，例：DateTimeUtils#DATE_TIME_PATTERN  ，没有可以补充
     * @return String
     * @author weike
     * @lastEditor weike
     * @createTime 2020/11/13 8:36
     * @editTime
     **/
    public static String formatDateTimeToString(LocalDateTime localDateTime, String format) {
        if (localDateTime == null) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return localDateTime.format(formatter);
        } catch (DateTimeParseException e) {
        }
        return null;
    }

    /**
     * 指定日期的最小时间点 2020-10-10 00:00:00
     */
    public static LocalDateTime dayOfMin(LocalDate date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.of(date, LocalTime.MIN);
    }

    /**
     * 指定日期的最大时间点 2020-10-10 23:59:59.999
     */
    public static LocalDateTime dayOfMax(LocalDate date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.of(date, LocalTime.MAX);
    }

    /**
     * localDate 转 int
     */
    public static int formatIntBatch(LocalDate date) {
        if (date == null) {
            return 0;
        }
        return Integer.parseInt(DateTimeFormatter.ofPattern(DATE_PATTERN_DAY).format(date));
    }

    /**
     * 将LocalDateTime转为 MM.dd
     */
    public static String formatDay(LocalDateTime time) {
        if (time == null) {
            return "";
        }
        return DateTimeFormatter.ofPattern(DATE_DAY2).format(time);
    }

    /**
     * 将LocalDate转为 MM.dd
     */
    public static String formatDay(LocalDate date) {
        if (date == null) {
            return "";
        }
        return DateTimeFormatter.ofPattern(DATE_DAY2).format(date);
    }

    /**
     * 格式化批次号为localDate
     *
     * @param batch 批次号，形如：20210927
     */
    public static LocalDate formatBatchToLocalDate(String batch) {
        if (StrUtils.isEmpty(batch) || batch.length() != 8) {
            return null;
        }
        String format = String.format("%s-%s-%s", batch.substring(0, 4), batch.substring(4, 6),
            batch.substring(6, 8));
        return stringDateToLocalDate(format);
    }


    /**
     * 将LocalDate转为 yyyyMMdd
     */
    public static long formatLongBatch(LocalDate date) {
        if (date == null) {
            return 0L;
        }
        return Long.parseLong(DateTimeFormatter.ofPattern(DATE_PATTERN_DAY).format(date));
    }

    public static long betweenSecond(LocalDateTime date1, LocalDateTime date2) {
        return ChronoUnit.SECONDS.between(date1, date2);
    }

    public static long betweenMillis(LocalDateTime date1, LocalDateTime date2) {
        return ChronoUnit.MILLIS.between(date1, date2);
    }

    public static String formatDateToString(LocalDate localDate, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return localDate.format(formatter);
        } catch (DateTimeParseException e) {
        }
        return null;
    }

}
