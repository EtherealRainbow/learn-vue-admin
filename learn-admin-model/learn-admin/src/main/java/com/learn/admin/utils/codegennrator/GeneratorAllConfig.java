package com.learn.admin.utils.codegennrator;

import lombok.Data;

/**
 * TODO
 *
 * @author lijun
 * @program learn-admin-model
 * @date 2022/6/30 15:44
 */
@Data
public class GeneratorAllConfig {
    String modelName = "learn-admin";
    // 设置输出目录
    String path = System.getProperty("user.dir")+"\\"+modelName+"\\"+"src\\main\\java";
    // 设置mapperXml生成路径
    String pathMapperXml = System.getProperty("user.dir")+"\\"+modelName+"\\"+"src\\main\\resources\\mapper";
    //作者名
    String author = "lijun";
    //注释时间
    String commentDate = "yyyy年MM月dd日";

}
