package com.luckyDL.ftpcameldemo.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFileMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@Component
public class DataProcessor implements Processor {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(DataProcessor.class);

    @Value("${ftp.local-dir}")
    private String fileDir;

    @Override
    public void process(Exchange exchange) throws Exception {
        GenericFileMessage<RandomAccessFile> inFileMessage = (GenericFileMessage<RandomAccessFile>) exchange.getIn();
        String fileName = inFileMessage.getGenericFile().getFileName();
        String filePath = fileDir + '/' + fileName;
        readZip(filePath);
        deleteFile(filePath);
    }

    private void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 读取zip file中的文件内容
     *
     * @param filePath 文件路径
     */
    private void readZip(String filePath) throws Exception {
        ZipFile zipFile = new ZipFile(filePath);
        InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        ZipEntry ze;
        while ((ze = zipInputStream.getNextEntry()) != null) {
            if (ze.isDirectory()) {
                // 如果需要递归处理子目录，在此处实现
                continue;
            }
            logger.info("DetectorDataProcessor.readZip: Start reading {} in {}", ze.getName(), filePath);
            if (ze.getSize() > 0) {
                BufferedReader br = new BufferedReader(new InputStreamReader(zipFile.getInputStream(ze)));
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
                br.close();
            }
        }
        zipInputStream.closeEntry();
        inputStream.close();
        zipFile.close();
    }
}
