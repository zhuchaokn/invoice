package com.stonegate.invoice.autoinvoice.bean;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chao.zhu created on 15/5/19 下午10:24
 * @version 1.0
 */
public class Invoice implements Serializable {

    private static final long serialVersionUID = -329519235693310354L;
    private int id;
    private String key;
    // 上班时间
    private String beginTime;
    // 下班时间
    private String endTime;
    private String from;
    private String to;
    private String use;
    private Date date;
    private double taxiPrice;
    private String getOn;
    private int waiting;

    public String getBeginTime() {
        return beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getGetOn() {
        return getOn;
    }

    public void setGetOn(String getOn) {
        this.getOn = getOn;
    }

    public int getWaiting() {
        return waiting;
    }

    public void setWaiting(int waiting) {
        this.waiting = waiting;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getTaxiPrice() {
        return taxiPrice;
    }

    public void setTaxiPrice(double taxiPrice) {
        this.taxiPrice = taxiPrice;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
