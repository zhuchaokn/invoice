package com.stonegate.invoice.autoinvoice.service;

import com.stonegate.invoice.autoinvoice.bean.Invoice;
import com.stonegate.invoice.autoinvoice.dao.InvoiceDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chao.zhu created on 15/5/19 下午10:23
 * @version 1.0
 */
@Service
public class InvoiceService {
    @Resource
    private InvoiceDao invoiceDao;

    public void saveInvoice(List<Invoice> invoice) {
        invoiceDao.insertInvoice(invoice);
    }

    public List<Invoice> getInvoice(String key) {
        return invoiceDao.getInvoiceByKey(key);
    }
}
