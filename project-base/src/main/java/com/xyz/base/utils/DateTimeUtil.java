package com.xyz.base.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author ZKKzs
 **/
public class DateTimeUtil {

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(DATE_PATTERN);

    /**
     * 十分钟
     */
    private static final long EXPIRATION_TIME = 10 * 60 * 1000;

    public static Long currentTime() {
        return System.currentTimeMillis();
    }

    public static Long expirationTime(Long currentTime) {
        return currentTime + EXPIRATION_TIME;
    }

    public static String getCurrentDate() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDateTime now = LocalDateTime.now();
        return now.format(format);
    }

    public static String dateToString(Date date) {
        return FORMATTER.format(date);
    }

}
