package com.stonegate.invoice.autoinvoice.bean;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author chao.zhu created on 15/5/30 下午3:04
 * @version 1.0
 */
public class Day {
    // 八小时时差
    private final long timeJet = TimeUnit.HOURS.toMillis(8);
    private int day;
    private int hour;
    private int minute;
    private int second;
    private long time;

    public Day(int day, int hour, int minute, int second) {
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.time = TimeUnit.DAYS.toMillis(day) + TimeUnit.HOURS.toMinutes(hour) + TimeUnit.MINUTES.toMillis(minute)
                + TimeUnit.SECONDS.toMillis(second);
        this.time -= timeJet;
    }

    public Day(long time) {
        this.time = time;
        time += timeJet;
        this.day = (int) (TimeUnit.MILLISECONDS.toDays(time));
        long dayTime = time % TimeUnit.DAYS.toMillis(1);
        this.hour = (int) TimeUnit.MILLISECONDS.toHours(dayTime);
        this.minute = (int) TimeUnit.MILLISECONDS.toMinutes(dayTime % TimeUnit.HOURS.toMillis(1));
        this.second = (int) TimeUnit.MILLISECONDS.toSeconds(dayTime % TimeUnit.MINUTES.toMillis(1));
    }

    public Day(Date date) {
        this(date.getTime());
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public Date getDate() {
        return new Date(time);
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getAPTime() {
        String tag = hour >= 12 ? "PM" : "AM";
        hour = hour % 12;
        return String.format("%d:%d:%d",hour,minute,second);
    }
}
