package com.luckyDL.ftpcameldemo.config;

import com.luckyDL.ftpcameldemo.processor.DataProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DownloadRoute extends RouteBuilder {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(DownloadRoute.class);

    @Value("${ftp.url}")
    private String url;
    @Value("${ftp.local-dir}")
    private String downloadLocation;

    @Autowired
    private DataProcessor dataProcessor;

    @Override
    public void configure() throws Exception {
        from(url)
                .to("file:" + downloadLocation)
                .process(dataProcessor)
                .log(LoggingLevel.INFO, logger, "Download file ${file:name} complete.");
    }
}
