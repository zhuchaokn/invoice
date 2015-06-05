package com.stonegate.invoice.autoinvoice;

import org.junit.Test;

import java.util.Date;

public class DateUtilTest {

    @Test
    public void testFormat() throws Exception {
        System.out.println(DateUtil.format(new Date()));
    }
}