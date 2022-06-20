package com.learn.admin.common.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * 文件上床大小设置
 *
 * @author lijun
 * @program learn
 * @date 2021/5/6 11:54
 */
@Configuration
public class FileUploadConfiuration {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件大小200mb
        factory.setMaxFileSize(DataSize.ofMegabytes(200L));
        //设置总上传数据大小1GB
        factory.setMaxRequestSize(DataSize.ofGigabytes(1L));

        return factory.createMultipartConfig();
    }

}
