package com.learn.admin.utils.codegennrator;

import lombok.Data;

/**
 * TODO
 *
 * @author lijun
 * @program learn-admin-model
 * @date 2022/6/30 15:43
 */
@Data
public class GeneratorConfig {

    //设置父包名
    String parent = "com.learn.admin";
    // 设置父包模块名
    String moduleName = "";
    //设置实体层
    String entity = "entity";
    //设置服务层
    String service = "service";
    //设置实现层
    String serviceImpl = "service.impl";
    //设置mapper层
    String mapper = "mapper";
    //设置controller层
    String controller = "controller";
    //其他层设置
    String other = "other";
    //表名
    String tableName = "";
    String tablePrefix = "";
    String create_time = "create_time";
    String update_time = "update_time";
    String author = "lijun";

}
