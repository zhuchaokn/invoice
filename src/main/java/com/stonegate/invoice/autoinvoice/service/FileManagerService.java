package com.stonegate.invoice.autoinvoice.service;

import com.google.common.io.Closer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @author chao.zhu created on 15/5/30 下午12:54
 * @version 1.0
 */
@Service
public class FileManagerService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final String path = getClass().getResource("/excels/").getPath();

    public File getTemplateFile() {
        return new File(path + "invoice.xls");
    }

    public File getNewExcelFile(String name) throws Exception {
        File template = new File(path + "invoice.xls");
        File emptyExcel = new File(path + name);
        copy(template, emptyExcel);
        return emptyExcel;
    }

    private void copy(File source, File target) throws Exception {
        if (!source.exists()) {
            logger.error("{} not found", source.getAbsolutePath());
        }
        if (target.exists()) {
            target.delete();
            target.createNewFile();
        }
        Closer closer = Closer.create();
        FileInputStream inputStream = new FileInputStream(source);
        closer.register(inputStream);
        FileOutputStream outputStream = new FileOutputStream(target);
        closer.register(outputStream);
        FileChannel in = inputStream.getChannel();
        FileChannel out = outputStream.getChannel();
        try {
            in.transferTo(0, in.size(), out);
        } catch (Exception e) {
            throw e;
        } finally {
            closer.close();
        }
    }
}
