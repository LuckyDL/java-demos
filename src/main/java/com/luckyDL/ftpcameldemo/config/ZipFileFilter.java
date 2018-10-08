package com.luckyDL.ftpcameldemo.config;

import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileFilter;
import org.springframework.stereotype.Component;

/**
 * ftp zip文件过滤器
 *
 * @author LuckyDL
 * @date 2018.10.08
 */
@Component
public class ZipFileFilter implements GenericFileFilter {

    @Override
    public boolean accept(GenericFile file) {
        System.out.println(file.getAbsoluteFilePath());
        return file.getFileName().endsWith(".zip") || file.isDirectory();
    }
}
