package com.stonegate.invoice.autoinvoice.service;

import com.google.common.collect.Lists;
import com.stonegate.invoice.autoinvoice.DateUtil;
import com.stonegate.invoice.autoinvoice.bean.Invoice;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config.xml" })
public class ExcelServiceTest {
    @Resource
    private ExcelService excelService;

    public static void main(String[] args) throws IOException, BiffException {
        Workbook book = Workbook.getWorkbook(new File(ExcelServiceTest.class.getResource("/excels/invoice.xls")
                .getFile()));
        System.out.println(book.getSheetNames()[0]);
        Sheet sheet = book.getSheet(0);
        System.out.println(sheet.getName());

        for (int j = 0; j < sheet.getRows(); j++) {
            for (int i = 0; i < sheet.getColumns(); i++) {
                Cell cell = sheet.getCell(i, j);
                System.out.println("" + j + ":" + i + ":" + cell.getContents());
            }
        }
        book.close();
    }

    @Test
    public void writeExcel() throws Exception {
        Invoice invoice = new Invoice();
        invoice.setBeginTime(DateUtil.format(new Date()));
        invoice.setDate(new Date());
        invoice.setEndTime(DateUtil.format(new Date()));
        invoice.setGetOn(DateUtil.format(new Date()));
        invoice.setTaxiPrice(60);
        invoice.setWaiting(2);
        BeanCopier copier = BeanCopier.create(Invoice.class, Invoice.class, false);
        Invoice night = new Invoice();
        copier.copy(invoice, night, null);
        night.setGetOn(DateUtil.format(new Date(new Date().getTime() + TimeUnit.HOURS.toMillis(6))));
        excelService.writeToExcel("673515939", Lists.newArrayList(invoice, invoice, night, invoice, night, night));
    }
}