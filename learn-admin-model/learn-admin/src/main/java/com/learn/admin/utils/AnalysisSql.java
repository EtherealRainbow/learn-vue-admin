package com.learn.admin.utils;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.NamedExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.util.*;

/**
 * sql解析
 *
 * @param
 * @return
 * @author lijun
 * @date 2022/6/17
 **/
public class AnalysisSql {

    static String sql1 = "select t1.f1,t1.f2,t2.id,count(*) from table t1 left join table1 t2 right join (select * from table2) t3 where t1.id='12121' or (t1.id between 1 and 3 and t1.id>'22112') group by t.f1 Order by t.f1 desc,tf2 asc limit 1,20";
    static String sql2 = "insert into table(f1,f2) values (1,2)";
    static String sql3 = "update table set f1=2,f2=3 where f1=1212";
    static String sql4 = "delete from table where 1=1 AND a=2";

    public static void main(String[] args) {
        selectSql();
//        Map<String, Object> insertSqlMap = insertSql(sql2);
//        System.out.println(insertSqlMap);
//        insertSql(sql2);
//        Map<String, Object> stringObjectMap = updateSql(sql3);
//        System.out.println(stringObjectMap);
//        Map<String, Object> deleteSqlMap = deleteSql(sql4);
//        System.out.println(deleteSqlMap.get("where"));
    }

    //解析sql
    public static void selectSql() {
        System.out.println("=================测试查询==================");
        try {
            Select select = (Select) CCJSqlParserUtil.parse(sql1);
            TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
            List<String> tableList = tablesNamesFinder.getTableList(select);
            PlainSelect plain = (PlainSelect) select.getSelectBody();
            List<Join> joins = plain.getJoins();
            for (Join join : joins) {
                FromItem rightItem = join.getRightItem();
                if (rightItem instanceof Table) {
                    Table table = (Table) (rightItem);
                    System.out.println("连接类型:" + joinTypeStr(join) + "         表：" + table.getName() + "           别名：" + table.getAlias());
                } else if (rightItem instanceof SubSelect) {
                    SubSelect subSelect = (SubSelect) (rightItem);
                    System.out.println("连接类型:" + joinTypeStr(join) + "         子查询：" + subSelect.getSelectBody() + "           别名：" + rightItem.getAlias());
                }
            }
            List<SelectItem> selectItems = plain.getSelectItems();
            for (SelectItem selectItem : selectItems) {
                SelectExpressionItem selectExpressionItem = (SelectExpressionItem) selectItem;
                Expression expression = selectExpressionItem.getExpression();
                //判断表达式是否是函数
                if (expression instanceof Function) {
                    Function function = (Function) expression;
                    NamedExpressionList namedParameters = function.getNamedParameters();
                    if (namedParameters != null) {
                        List<Expression> expressions = namedParameters.getExpressions();
                        System.out.println(expressions);
                    }
                    System.out.println("函数：" + ((Function) expression).getName());
                    boolean allColumns = function.isAllColumns();
                    System.out.println("传入的是全部列：" + allColumns);
                    //判断表达式是否是列
                } else if (expression instanceof Column) {
                    System.out.println("查询值：" + ((Column) expression).getColumnName());
                }
            }
            System.out.println("表名:" + tableList);
            Expression where = plain.getWhere();
            if (where != null) {
                System.out.println("条件:" + where);
            }

            //排序
            List<OrderByElement> orderByElements = plain.getOrderByElements();
            if (Objects.nonNull(orderByElements)) {
                for (OrderByElement orderByElement : orderByElements) {
                    Expression expression = orderByElement.getExpression();
                    if (expression instanceof Column) {
                        Column column = (Column) (expression);
                        System.out.println("排序字段:" + column.getColumnName() + "," + (orderByElement.isAsc() ? "正序" : "倒序"));
                    }
                }
            }


            //获取分组
            GroupByElement groupBy = plain.getGroupBy();
            if ( Objects.nonNull(groupBy)) {
                List<Expression> groupByExpressions = groupBy.getGroupByExpressions();
                for (Expression groupByExpression : groupByExpressions) {
                    if (groupByExpression instanceof Column) {
                        Column column = (Column) (groupByExpression);
                        System.out.println("分组字段:" + column.getColumnName());
                    }
                }
            }

            //分页
            Limit limit = plain.getLimit();
            if(Objects.nonNull(limit)){
                System.out.println("行:"+limit.getRowCount());
                System.out.println("偏移量:"+limit.getOffset());
            }
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        System.out.println("=================测试查询==================");
    }

    public static Map<String,Object> insertSql(String sql) {
        System.out.println("=================测试插入sql==================");
        System.out.println("测试sql:" + sql);
        Map<String,Object> resultMap = new HashMap<>(8);

        try {
            Map<String,Object> field = new HashMap<>(8);
            Insert insert = (Insert) CCJSqlParserUtil.parse(sql);
            System.out.println("插入的表" + insert.getTable().getName());
            System.out.println("插入的列" + insert.getColumns());
            if (Objects.nonNull(insert.getSelect())) {
                SelectBody selectBody = insert.getSelect().getSelectBody();
                System.out.println("来自：" + selectBody);
            } else {
                resultMap.put("tableName",insert.getTable());
                List<Column> columns = insert.getColumns();
                ItemsList itemsList = insert.getItemsList();
                String[] split = itemsList.toString().replace("(", "").replace(")", "").split(",");
                int size = columns.size();
                for (int i = 0; i < size; i++) {
                    Column column = columns.get(i);
                    field.put(column.getColumnName(),split[i]);
                }
                System.out.println("插入的值" + insert.getItemsList());
            }
            resultMap.put("field",field);
            resultMap.put("where",null);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    public static Map<String,Object> updateSql(String sql) {
        System.out.println("=================测试更新sql==================");
        System.out.println("测试sql:" + sql);
        Map<String,Object> resultMap = new HashMap<>(8);
        Map<String,Object> field = new HashMap<>(8);
        try {
            Update update = (Update) CCJSqlParserUtil.parse(sql);
            System.out.println("更新的表" + update.getTable());
            System.out.println("更新的列" + update.getColumns());
            System.out.println("更新的值" + update.getExpressions());
            System.out.println("条件" + update.getWhere());
            List<Column> columns = update.getColumns();
            int size = columns.size();
            for (int i = 0; i < size; i++) {
                field.put(columns.get(i).toString(),update.getExpressions().get(i));
            }


//            field.put("columns",columnSb.substring(0,columnSb.length()-1));
//            field.put("values",columnSb.substring(0,columnSb.length()-1));
            resultMap.put("tableName",update.getTable());
            resultMap.put("field",field);
            resultMap.put("where",update.getWhere());
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    public static Map<String,Object> deleteSql(String sql) {
        Map<String,Object> resultMap = new HashMap<>(8);
        System.out.println("=================测试删除sql==================");
        System.out.println("测试sql:" + sql);
        try {
            Delete delete = (Delete) CCJSqlParserUtil.parse(sql);
            resultMap.put("tableName",delete.getTable());
            resultMap.put("field",null);
            resultMap.put("where",delete.getWhere());
            System.out.println("删除的表" + delete.getTable().getName());
            System.out.println("条件的列" + delete.getWhere().toString());
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    public static String joinTypeStr(Join join) {
        if (join.isLeft()) {
            return "左连接";
        }
        if (join.isRight()) {
            return "左连接";
        }
        if (join.isFull()) {
            return "全连接";
        }
        if (join.isCross()) {
            return "交叉连接";
        }
        return null;
    }
}

