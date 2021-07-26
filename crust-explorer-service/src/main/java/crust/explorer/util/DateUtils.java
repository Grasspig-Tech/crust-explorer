/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package crust.explorer.util;

import crust.explorer.pojo.vo.CountHistoryVO;
import crust.explorer.pojo.vo.EraStatCountVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期处理
 *
 * @author Lishuai 15811276045@126.com
 */
@Slf4j
public class DateUtils {
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 时间格式(M-dd)
     */
    public final static String MM_DD = "MM-dd";

    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public final static String NOW_H00 = "yyyy-MM-dd HH:00:00";
    public final static String NOW_H = "HH";

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date 日期
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }


    public static String nowH00String() {
        return format(new Date(), NOW_H00);
    }

    public static String nowHString() {
        return format(new Date(), NOW_H);
    }

    public static Integer nowHInt() {
        return Integer.valueOf(nowHString());
    }

    public static String nowDayString() {
        return format(new Date(), DATE_PATTERN);
    }

    public static String nowString() {
        return format(new Date(), DATE_TIME_PATTERN);
    }

    public static List<CountHistoryVO> buildCountHistory1h() {
        // 2小时跑一次
        List<CountHistoryVO> count1h = new ArrayList<>(49);
        Date nowH = DateUtils.parse(DateUtils.nowH00String(), DateUtils.DATE_TIME_PATTERN);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowH);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 49);
        Date current = calendar.getTime();
        log.info("buildCountHistory1h current:{}", DateUtils.format(current, DateUtils.DATE_TIME_PATTERN));
        for (int i = 0; i < 49; i++) {
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 1);
            current = calendar.getTime();
            CountHistoryVO extrinsicVO = CountHistoryVO.builder().time(current).timeStr(DateUtils.format(current, DateUtils.DATE_TIME_PATTERN)).build();
            count1h.add(extrinsicVO);
            log.info("buildCountHistory1h i:{}, current:{}", i, DateUtils.format(current, DateUtils.DATE_TIME_PATTERN));
        }
        return count1h;
    }

    public static List<CountHistoryVO> buildCountHistory6h() {
        List<CountHistoryVO> count6h = new ArrayList<>(41);
        Integer nowHInt = DateUtils.nowHInt();
        Date last = new Date();
        String Hour = " 00:00:00";
        if (nowHInt <= 6) {
            Hour = " 00:00:00";
        } else if (nowHInt <= 12) {
            Hour = " 06:00:00";
        } else if (nowHInt <= 18) {
            Hour = " 12:00:00";
        } else {
            Hour = " 18:00:00";
        }
        last = DateUtils.parse(DateUtils.nowDayString() + Hour, DateUtils.DATE_TIME_PATTERN);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(last);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 41 * 6);
        Date current = calendar.getTime();
        log.info("buildCountHistory6h current:{}", DateUtils.format(current, DateUtils.DATE_TIME_PATTERN));
        for (int i = 0; i < 41; i++) {
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 6);
            current = calendar.getTime();
            CountHistoryVO extrinsicVO = CountHistoryVO.builder().time(current).timeStr(DateUtils.format(current, DateUtils.DATE_TIME_PATTERN)).build();
            count6h.add(extrinsicVO);
            log.info("buildCountHistory6h i:{}, current:{}", i, DateUtils.format(current, DateUtils.DATE_TIME_PATTERN));
        }
        return count6h;
    }

    public static List<CountHistoryVO> buildCountHistory1d() {
        // 12小时跑一次
        List<CountHistoryVO> count1d = new ArrayList<>(30);
        String nowDayString = DateUtils.nowDayString();
        Date nowD = DateUtils.parse(nowDayString, DATE_PATTERN);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowD);
        log.info("buildCountHistory1d nowD:{}", DateUtils.format(nowD, DateUtils.DATE_PATTERN));
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        Date current = null;
        int i = 0;
        for (; ; ) {
            if (i > 0) {
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
            }
            current = calendar.getTime();
            CountHistoryVO extrinsicVO = CountHistoryVO.builder().time(current).timeStr(DateUtils.format(current, DateUtils.DATE_PATTERN)).build();
            count1d.add(extrinsicVO);
            log.info("buildCountHistory1d i:{}, current:{}", i, DateUtils.format(current, DateUtils.DATE_PATTERN));
            if (DateUtils.format(nowD, DateUtils.DATE_PATTERN).equals(DateUtils.format(current, DateUtils.DATE_PATTERN))) {
                break;
            }
            i++;
        }
        return count1d;
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date    日期
     * @param pattern 格式，如：DateUtils.DATE_TIME_PATTERN
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 字符串转换成日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期的格式，如：DateUtils.DATE_TIME_PATTERN
     */
    public static Date stringToDate(String strDate, String pattern) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.parseLocalDateTime(strDate).toDate();
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date    日期
     * @param pattern 格式，如：DateUtils.DATE_TIME_PATTERN
     * @return 返回yyyy-MM-dd格式日期
     */
    public static Date parse(String date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            try {
                return df.parse(date);
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 根据周数，获取开始日期、结束日期
     *
     * @param week 周期  0本周，-1上周，-2上上周，1下周，2下下周
     * @return 返回date[0]开始日期、date[1]结束日期
     */
    public static Date[] getWeekStartAndEnd(int week) {
        DateTime dateTime = new DateTime();
        LocalDate date = new LocalDate(dateTime.plusWeeks(week));

        date = date.dayOfWeek().withMinimumValue();
        Date beginDate = date.toDate();
        Date endDate = date.plusDays(6).toDate();
        return new Date[]{beginDate, endDate};
    }

    public static Date getMonthStartTime() {
        Calendar calendar = Calendar.getInstance();
        //获取当月前的月的起始时间和结束时间
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);//设置当月的起始时间
        return calendar.getTime();
    }

    public static Date getMonthEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(
                Calendar.DAY_OF_MONTH));//设置当月结束天为当月的最大天，如：9月份最大天为30，此时设置天为30
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE), 23, 59, 59);//设置当月的结束时间
        return calendar.getTime();
    }

    public static Integer diffDate(Date firstDate, Date secondDate) {
        DateFormat df = new SimpleDateFormat(DATE_PATTERN);
        try {
            Date d1 = df.parse(DateUtils.format(firstDate));
            Date d2 = df.parse(DateUtils.format(secondDate));
            //这样得到的差值是微秒级别
            long diff = d1.getTime() - d2.getTime();
            long days = diff / (1000 * 60 * 60 * 24);

            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            System.out.println("" + days + "天" + hours + "小时" + minutes + "分");
            return Integer.parseInt(days + "");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 对日期的【秒】进行加/减
     *
     * @param date    日期
     * @param seconds 秒数，负数为减
     * @return 加/减几秒后的日期
     */
    public static Date addDateSeconds(Date date, int seconds) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusSeconds(seconds).toDate();
    }

    /**
     * 对日期的【分钟】进行加/减
     *
     * @param date    日期
     * @param minutes 分钟数，负数为减
     * @return 加/减几分钟后的日期
     */
    public static Date addDateMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minutes).toDate();
    }

    /**
     * 对日期的【小时】进行加/减
     *
     * @param date  日期
     * @param hours 小时数，负数为减
     * @return 加/减几小时后的日期
     */
    public static Date addDateHours(Date date, int hours) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusHours(hours).toDate();
    }

    /**
     * 对日期的【天】进行加/减
     *
     * @param date 日期
     * @param days 天数，负数为减
     * @return 加/减几天后的日期
     */
    public static Date addDateDays(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }

    /**
     * 对日期的【周】进行加/减
     *
     * @param date  日期
     * @param weeks 周数，负数为减
     * @return 加/减几周后的日期
     */
    public static Date addDateWeeks(Date date, int weeks) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusWeeks(weeks).toDate();
    }

    /**
     * 对日期的【月】进行加/减
     *
     * @param date   日期
     * @param months 月数，负数为减
     * @return 加/减几月后的日期
     */
    public static Date addDateMonths(Date date, int months) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(months).toDate();
    }

    /**
     * 对日期的【年】进行加/减
     *
     * @param date  日期
     * @param years 年数，负数为减
     * @return 加/减几年后的日期
     */
    public static Date addDateYears(Date date, int years) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusYears(years).toDate();
    }

    public static void main(String[] args) {
        int a = 1626178596;
        int b = 1626156996;
        int c = 1626156990;
        int d = 1626135390;
        System.out.println((a - b));
        System.out.println((c - d));

        List<EraStatCountVO> eras = new ArrayList<>();
        EraStatCountVO era804 = EraStatCountVO.builder().era(804)
                .startBlockTimestamp(1626156996).endBlockTimestamp(1626178590)
                .startBlockNum(2399688).endBlockNum(2403258).build();
        int start = era804.getStartBlockTimestamp();
        int end = era804.getEndBlockTimestamp();
        int sb = era804.getStartBlockNum();
        int eb = era804.getEndBlockNum();
        int i = era804.getEra();
        for (; ; ) {
            if (i > 0) {
                EraStatCountVO current = EraStatCountVO.builder().era(i)
                        .startBlockTimestamp(start).endBlockTimestamp(end)
                        .startBlockNum(sb).endBlockNum(eb).build();
                eras.add(current);
                start -= 21600;
                end -= 21600;
            } else {
                break;
            }
            i--;
        }
        System.out.println(eras);

    }
}
