package com.stonegate.invoice.autoinvoice.service;

import com.stonegate.invoice.autoinvoice.DateUtil;
import com.stonegate.invoice.autoinvoice.bean.Day;
import com.stonegate.invoice.autoinvoice.bean.Invoice;
import jxl.Workbook;
import jxl.write.*;
import jxl.write.Number;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.text.ParseException;
import java.util.List;

/**
 * @author chao.zhu created on 15/5/28 下午11:26
 * @version 1.0
 */
@Service
public class ExcelService {
    @Resource
    private FileManagerService fileManagerService;

    public File writeToExcel(String name, List<Invoice> invoiceList) throws Exception {
        File target = fileManagerService.getNewExcelFile(name);
        Workbook template = Workbook.getWorkbook(fileManagerService.getTemplateFile());
        WritableWorkbook workbook = Workbook.createWorkbook(target, template);
        try {
            WritableSheet daySheet = workbook.getSheet(0);
            WritableSheet nightSheet = workbook.getSheet(1);
            int dayIndex = 9;
            int nightIndex = 9;
            for (Invoice invoice : invoiceList) {
                // 白天行程
                WritableSheet sheet;
                int index = 0;
                Day day = new Day(DateUtil.parse4y2m2d2H2M2s(invoice.getGetOn()));
                if (day.getHour() < 23 && day.getHour() > 5) {
                    sheet = daySheet;
                    index = dayIndex++;
                } else {
                    sheet = nightSheet;
                    index = nightIndex++;
                }
                writeRow(index, sheet, invoice, day);
            }
            workbook.write();
        } catch (Exception e) {
            throw e;
        } finally {
            template.close();
            workbook.close();
        }
        return target;
    }

    private void writeRow(int row, WritableSheet sheet, Invoice invoice, Day day) throws WriteException, ParseException {
        DateTime getOnDay = new DateTime(1, row, DateUtil.parse4y2m2d2H2M2s(invoice.getGetOn()));
        sheet.addCell(getOnDay);
        sheet.addCell(new Label(2, row, day.getAPTime()));
        sheet.addCell(new Number(9, row, invoice.getWaiting()));
        sheet.addCell(new Number(11, row, invoice.getTaxiPrice()));
    }
}
