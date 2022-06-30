package com.learn.admin.utils.codegennrator;

import lombok.Data;

/**
 * TODO
 *
 * @author lijun
 * @program learn-admin-model
 * @date 2022/6/30 15:46
 */
@Data
public class GeneratorConnection {

    //数据库IP地址
    String databaseIp = "localhost";
    //数据库端口号
    String spot = "3306";
    //数据库名
    String databaseName = "blog";
    //数据库账号名
    String mysqlUser = "root";
    //数据库密码
    String mysqlPassword = "root";

    String mysqlBase = "jdbc:mysql://" + databaseIp + ":" + spot + "/" + databaseName;

}
