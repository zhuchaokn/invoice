package com.stonegate.invoice.autoinvoice.controller;

import com.qunar.hotel.qta.base.api.APIResponse;
import com.stonegate.invoice.autoinvoice.bean.Invoice;
import com.stonegate.invoice.autoinvoice.service.ExcelService;
import com.stonegate.invoice.autoinvoice.service.InvoiceService;
import org.jboss.logging.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.List;

/**
 * @author chao.zhu created on 15/5/19 下午10:23
 * @version 1.0
 */
@Controller
@RequestMapping("/invoice")
public class InvoiceController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private InvoiceService invoiceService;
    @Resource
    private ExcelService excelService;

    @RequestMapping("upload")
    @ResponseBody
    public void uploadJsonp(@RequestParam String callBack, @RequestParam String userId, @RequestParam String data,
            HttpServletResponse response) throws IOException {

        data = URLDecoder.decode(data, "utf-8");
        logger.info("get calback:{},data:{}", callBack, data);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/javascript");
        Writer writer = response.getWriter();
        writer.write(String.format("%s()", callBack));
        writer.flush();
        writer.close();
    }

    @RequestMapping("save")
    @ResponseBody
    public APIResponse<String> save(@RequestBody List<Invoice> invoiceList) {
        invoiceService.saveInvoice(invoiceList);
        return APIResponse.returnSuccess("success");
    }

    @RequestMapping("query")
    @ResponseBody
    public APIResponse<List<Invoice>> query(@Param String key) {
        List<Invoice> invoiceList = invoiceService.getInvoice(key);
        if (CollectionUtils.isEmpty(invoiceList)) {
            return APIResponse.returnFail("not found");
        }
        return APIResponse.returnSuccess(invoiceList);
    }

    @RequestMapping("download")
    public void download(@RequestParam String key, HttpServletResponse response) throws Exception {
        List<Invoice> invoiceList = invoiceService.getInvoice(key);
        // todo
        String name = key + ".xls";
        File file = excelService.writeToExcel(name, invoiceList);
        response.setContentType("application/x-excel");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + name);
        response.setHeader("Content-Length", String.valueOf(file.length()));
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        try {

            byte[] buffer = new byte[4096];
            int len = inputStream.read(buffer);
            while (len > 0) {
                out.write(buffer, 0, len);
                len = inputStream.read(buffer);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            inputStream.close();
            out.flush();
            out.close();
        }
    }
}
