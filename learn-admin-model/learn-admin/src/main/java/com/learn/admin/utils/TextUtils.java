package com.learn.admin.utils;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.*;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlDeleteStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlUpdateStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.alibaba.druid.util.JdbcConstants;
import java.util.*;

/**
 * TODO
 *
 * @author lijun
 * @program learn-admin-model
 * @date 2022/6/16 16:47
 */
public class TextUtils {


    static String sql1 = "select t1.f1,t1.f2,t2.id,count(*) from table t1 left join table1 t2 right join (select * from table2) t3 where t1.id='12121' or (t1.id between 1 and 3 and t1.id>'22112') group by t.f1 Order by t.f1 desc,tf2 asc limit 1,20";
    static String sql2 = "insert into test (id,status,name,ce,acc) values (29,'P','lll','sxsx','Arferwg')";
    static String sql3 = "update abc set status='P',name='张三' where id=20";
    static String sql4 = "delete from test where 1=1 AND a=2";

    public static void main(String[] args) {
        TextUtils sqlServerParser = new TextUtils();
//        Map<String, Object> parse = sqlServerParser.parse(sql3);
//        Map<String, Object> parse2 = sqlServerParser.parse(sql2);
        Map<String, Object> select = sqlServerParser.parse(sql1);
        Map<String, Object> insert = sqlServerParser.parse(sql2);
        Map<String, Object> update = sqlServerParser.parse(sql3);
        Map<String, Object> delete = sqlServerParser.parse(sql4);
        System.out.println("=================返回结果==================");
//        System.out.println(parse2);
    }


    public Map<String, Object> parse(String statement) {

        // 使用druid解析语句
        // 第一个参数为SQL语句
        // 第二个参数为解析的数据库类型
        List<SQLStatement> statementList = SQLUtils.parseStatements(statement, JdbcConstants.MYSQL);
        // 单语句解析，只有一条数据
        if (!statement.isEmpty()) {
            SQLStatement sqlStatement = statementList.get(0);
            // 插入语句解析
            if (sqlStatement instanceof MySqlInsertStatement) {
                return insertSql(sqlStatement);
            } else if (sqlStatement instanceof MySqlUpdateStatement) {
                return updateSql(sqlStatement);
            } else if (sqlStatement instanceof SQLSelectStatement ) {
                return selectSql(sqlStatement);
            } else if (sqlStatement instanceof MySqlDeleteStatement) {
                return deleteSql(sqlStatement);
            }
        }
        return null;
    }

    public Map<String, Object> selectSql(SQLStatement statement) {
        System.out.println("=================测试查询sql==================");
        Map<String,Object> resultMap = new HashMap<>(8);
        // 只考虑查询语句
        SQLSelectStatement  sqlSelectStatement = (SQLSelectStatement ) statement;
        MySqlSelectQueryBlock select = (MySqlSelectQueryBlock)sqlSelectStatement.getSelect().getQuery();
        MySqlOutputVisitor where = new MySqlOutputVisitor(new StringBuilder());

        MySqlOutputVisitor tableName = new MySqlOutputVisitor(new StringBuilder());
        select.getFrom().accept(tableName);

        select.getWhere().accept(where);

        List<SQLSelectItem> selectList = select.getSelectList();
        StringBuilder fieldSb = new StringBuilder();
        for (SQLSelectItem sqlSelectItem : selectList) {
            fieldSb.append(sqlSelectItem.getExpr()+",");
        }

        StringBuilder sqlSB = new StringBuilder();
        sqlSB.append("select _id from ");
        sqlSB.append(tableName.getAppender());
        sqlSB.append(" where ");
        sqlSB.append(where.getAppender());

        resultMap.put("tableName",tableName.getAppender());
        resultMap.put("field",fieldSb.substring(0,fieldSb.length()-1));
        resultMap.put("where",where.getAppender());
        resultMap.put("sql",sqlSB.toString());


        System.out.println("返回结果:");
        System.out.println(resultMap);
        return resultMap;
    }

    public Map<String, Object> insertSql(SQLStatement sqlStatement) {
        System.out.println("=================测试插入sql==================");
        Map<String, Object> resultMap = new HashMap<>(8);
        // 转换
        MySqlInsertStatement insertStatement = (MySqlInsertStatement) sqlStatement;
        // 获取列名
        List<SQLExpr> columns = insertStatement.getColumns();
        // 获取值
        List<SQLInsertStatement.ValuesClause> valuesList = insertStatement.getValuesList();
        int size = columns.size();
        Map<String, Object> field = null;
        for (SQLInsertStatement.ValuesClause valuesClause : valuesList) {
            field=new HashMap<>(4);
            for (int i = 0; i < size; i++) {
                List<SQLExpr> values = valuesClause.getValues();
                String columnName = ((SQLIdentifierExpr) columns.get(i)).getName();
                Object value = getValue(values.get(i));
                field.put(columnName, value);
            }
        }
        System.out.println("表名：" + insertStatement.getTableName().getSimpleName());
        // 获取表名
        System.out.println("插入数据的键值：" + field);
        System.out.println("插入条件：");
        resultMap.put("tableName", insertStatement.getTableName().getSimpleName());
        resultMap.put("field", field);
        resultMap.put("where", null);
        System.out.println("返回结果：");
        System.out.println(resultMap);
        return resultMap;
    }

    public Map<String, Object> updateSql(SQLStatement sqlStatement) {
        System.out.println("=================测试更新sql==================");
        Map<String, Object> resultMap = new HashMap<>(8);
        // 更新语句解析
        MySqlUpdateStatement updateStatement = (MySqlUpdateStatement) sqlStatement;

        // 获取更新的值和内容
        List<SQLUpdateSetItem> items = updateStatement.getItems();
        Map<String, Object> updateMap = new HashMap<>(items.size());
        for (SQLUpdateSetItem item : items) {
            updateMap.put(((SQLIdentifierExpr) item.getColumn()).getName(), getValue(item.getValue()));
        }
        SQLBinaryOpExpr where = (SQLBinaryOpExpr) updateStatement.getWhere();

        resultMap.put("tableName", updateStatement.getTableName().getSimpleName());
        resultMap.put("field", updateMap);
        resultMap.put("where", where);

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select _id from ");
        sqlBuilder.append(updateStatement.getTableName().getSimpleName());
        sqlBuilder.append(" where ");
        sqlBuilder.append(where);
        resultMap.put("sql",sqlBuilder.toString());

        System.out.println("返回结果：");
        System.out.println(resultMap);
        return resultMap;
    }

    public Map<String,Object> deleteSql(SQLStatement sqlStatement){
        System.out.println("=================测试删除sql==================");
        Map<String,Object> resultMap = new HashMap<>(8);
        MySqlDeleteStatement statement = (MySqlDeleteStatement)sqlStatement;
        SQLName name = statement.getTableName();
        SQLExpr where = statement.getWhere();
        resultMap.put("tableName",name.toString());
        resultMap.put("where",where.toString());
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select _id from ");
        sqlBuilder.append(name);
        sqlBuilder.append(" where ");
        sqlBuilder.append(where);
        resultMap.put("sql",sqlBuilder.toString());
        System.out.println("返回结果：");
        System.out.println(resultMap);
        return resultMap;
    }

    private static Object getValue(SQLExpr value) {
        // TODO 判断更多的种类
        if (value instanceof SQLIntegerExpr) {
            // 值是数字
            return ((SQLIntegerExpr) value).getNumber();
        } else if (value instanceof SQLCharExpr) {
            // 值是字符串
            return ((SQLCharExpr) value).getText();
        }
        return null;
    }

}
