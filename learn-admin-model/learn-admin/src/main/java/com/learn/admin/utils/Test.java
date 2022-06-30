package com.learn.admin.utils;


import com.learn.admin.utils.codegennrator.CodeAutoGenerator;
import com.learn.admin.utils.codegennrator.GeneratorConfig;

/**
 * TODO
 *
 * @author lijun
 * @program learn-shop
 * @date 2022/6/28 17:55
 */
public class Test {

    public static void main(String[] args) {
        GeneratorConfig config = new GeneratorConfig();
        //设置作者
        config.setAuthor("lijun");
        //模块名称
        config.setModuleName("learn-admin");
        config.setController("controller.test");
        config.setService("service.test");
        config.setServiceImpl("service.test.impl");
        config.setEntity("entity.test");
        config.setTableName("sys_user");
        config.setMapper("test");
        CodeAutoGenerator.codeGenerator(config);
    }

}
