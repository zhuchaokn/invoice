package com.stonegate.invoice.autoinvoice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chao.zhu created on 15/5/30 下午3:58
 * @version 1.0
 */
public class DateUtil {
    private static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy/MM/dd");
        }
    };
    private static ThreadLocal<SimpleDateFormat> y4m2d2H2m2s2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public static Date parse4y2m2d2H2M2s(String date) throws ParseException {
        return y4m2d2H2m2s2.get().parse(date);
    }

    public static String formate4y2m2d2H2m2s(Date date) {
        return y4m2d2H2m2s2.get().format(date);
    }

    public static String format(Date date) {
        return dateFormatThreadLocal.get().format(date);
    }
}
