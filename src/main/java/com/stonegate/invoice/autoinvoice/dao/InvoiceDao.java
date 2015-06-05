package com.stonegate.invoice.autoinvoice.dao;

import com.stonegate.invoice.autoinvoice.bean.Invoice;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chao.zhu created on 15/5/19 下午10:24
 * @version 1.0
 */
@Repository
public interface InvoiceDao {
    List<Invoice> getInvoiceByKey(String key);

    void insertInvoice(List<Invoice> invoiceList);
}
