package com.stonegate.invoice.autoinvoice.service;

import org.junit.Test;

import java.io.File;

public class FileManagerServiceTest {

    @Test
    public void testGetNewExcelFile() throws Exception {
        FileManagerService service = new FileManagerService();
        File file = service.getNewExcelFile("test");
    }
}