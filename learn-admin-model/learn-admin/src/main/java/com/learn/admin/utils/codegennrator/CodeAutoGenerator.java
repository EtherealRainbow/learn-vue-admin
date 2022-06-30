package com.learn.admin.utils.codegennrator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CodeAutoGenerator {

    public static void codeGenerator(GeneratorConfig generatorConfig){
        String modelName = "learn-admin";

        /************************************START包配置****************************************/
        //设置父包名
        String parent = generatorConfig.getParent();
        // 设置父包模块名
        String mName = generatorConfig.getModuleName();
        String moduleName = "";
        //设置实体层
        String entity = generatorConfig.getEntity();
        //设置服务层
        String service = generatorConfig.getService();
        //设置实现层
        String serviceImpl = generatorConfig.getServiceImpl();
        //设置mapper层
        String mapper = generatorConfig.getMapper();
        //设置controller层
        String controller = generatorConfig.getController();
        //其他层设置
        String other = generatorConfig.getOther();
        /************************************END包配置***********************************************/
        /************************************START策略配置******************************************/
        //设置需要生成的表名 String tableName =表名
        String tableName = generatorConfig.getTableName();

        //设置过滤表前缀 String tablePrefix = "tb";
        String tablePrefix = generatorConfig.getTablePrefix();
        //设置自动填充的字段
        String create_time =generatorConfig.getCreate_time();
        String update_time = generatorConfig.getUpdate_time();
        /************************************END策略配置********************************************/


        /************************************START全局配置************************************/
        GeneratorAllConfig generatorAllConfig = new GeneratorAllConfig();
        generatorAllConfig.setModelName(mName);
        // 设置输出目录
        String path = generatorAllConfig.getPath();
        // 设置mapperXml生成路径
        String pathMapperXml = generatorAllConfig.getPathMapperXml();
        //作者名
        String author = generatorConfig.getAuthor();
        //注释时间
        String commentDate = generatorAllConfig.getCommentDate();
        /************************************END全局配置***************************************/

        /************************************START数据源配置************************************/
        GeneratorConnection generatorConnection = new GeneratorConnection();
        //数据库IP地址
        String databaseIp = generatorConnection.getDatabaseIp();
//        String databaseIp = "192.168.1.204";
        //数据库端口号
//        String spot = "3307";
        String spot = generatorConnection.getSpot();
        //数据库名
//        String databaseName = "sjtbxt";
        String databaseName = generatorConnection.getDatabaseName();
        //数据库账号名
//        String mysqlUser = "sjtbxt";
        String mysqlUser = generatorConnection.getMysqlUser();
        //数据库密码
//        String mysqlPassword = "sjtbxt_420223";
        String mysqlPassword = generatorConnection.getMysqlPassword();
        //Mysql url配置 只需选择 String mysql = mysql5;或者 String mysql = mysql8; 其他无需修改
        String mysqlBase = "jdbc:mysql://" + databaseIp + ":" + spot + "/" + databaseName;
        String mysql5Base = "?characterEncoding=utf-8&useSSL=false";
        String mysql8Base = "?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false";

        String mysql5 = mysqlBase + mysql5Base;
        String mysql8 = mysqlBase + mysql8Base;
        //选择mysql5或者mysql8    String mysql = mysql5;或者 String mysql = mysql8;
        String mysql = mysql8;

        /************************************END数据源配置**************************************/

        FastAutoGenerator.create(mysql, mysqlUser, mysqlPassword)
                .globalConfig(builder -> {
                    builder.author(author) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .disableOpenDir()//关闭资源管理器
                            .outputDir(path) // 指定输出目录
                            .commentDate(commentDate)//注释时间
                    ;
                })
                .packageConfig(builder -> {
                    builder.parent(parent) // 设置父包名
                            .moduleName(moduleName) // 设置父包模块名
                            .entity(entity) //设置实体层
                            .service(service) //设置服务层
                            .serviceImpl(serviceImpl) //设置实现层
                            .mapper(mapper) //设置mapper层
                            .controller(controller) //设置controller层
                            .other(other) //其他层设置
                            .pathInfo(Collections.singletonMap(OutputFile.xml, pathMapperXml)); // 设置mapperXml生成路径
                })
                .strategyConfig((scanner, builder) -> {
                    builder.addInclude(getTables(tableName)) // 设置需要生成的表名
                            .addTablePrefix(tablePrefix)
                            .serviceBuilder() //开启service策略配置
                            .formatServiceFileName("%sService") //取消Service前的I
                            .controllerBuilder() //开启controller策略配置
                            .enableRestStyle() //配置restful风格
                            .enableHyphenStyle() //url中驼峰转连字符
                            .entityBuilder() //开启实体类配置
                            .addTableFills(new Column(create_time, FieldFill.INSERT)) //配置添加自动填充字段
                            .addTableFills(new Column(update_time, FieldFill.INSERT_UPDATE)) //添加和更新配置自动填充字段
                            .enableLombok() //开启lombok
                    //.enableChainModel()//开启lombok链式操作@Accessors(chain = true)
                    ;
                }).templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker 引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    //输入处理多个表的情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
