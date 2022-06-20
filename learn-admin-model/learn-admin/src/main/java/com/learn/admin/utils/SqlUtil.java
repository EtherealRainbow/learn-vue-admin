package com.learn.admin.utils;

import java.util.*;

/**
 * 解析insert语句获取列名与值
 * 调用静态方法map,参数为insert语句
 * @Author LuoYu.
 * @Date 2022/6/16 14:20
 */
public class SqlUtil {

    private static String trim(String str){
        String values = values(str);
        values = values.substring(values.indexOf("("));

        StringBuffer sb = new StringBuffer(values);
        boolean delete = true;
        boolean flag = true;
        int start = 0;
        while (flag){
            char c = sb.charAt(start);
            if(compare(c,'\'')){
                delete=!delete;
            }
            if((compare(c,'\n')&&delete)||(compare(c,' ')&&delete)){
                sb.deleteCharAt(start);
            }else{
                start++;
            }
            if(start >= sb.length()){
                break;
            }
        }
        return sb.toString();
    }

    private static String values(String str) {
        String ls = str.toLowerCase();
        int valueIndex = ls.indexOf("values");
        String values = str.substring(valueIndex);
        return values;
    }

    private static String[] columns(String str) {
        int start1 = str.indexOf("(");
        int end = str.indexOf(")");

        String values = str.substring(start1+1,end);
        values = values.replaceAll("\n","").replaceAll(" ","");
        return values.split(",");
    }

    private static List<String> params(String sql){
        String values = trim(sql);
        boolean flag = true;
        int startIndex = 0;
        int endIndex = 0;
        boolean param = false;
        boolean fh = false;
        int start = 1;
        int t = 0;
        boolean str = false;
        List<String> params = new ArrayList<>();
        while(flag){
            char pri = values.charAt(start-1);
            char c = values.charAt(start);
            if(!param&&(compare(pri,'(')||compare(pri,','))){
                if(compare(c,'\'')){
                    startIndex = start+1;
                    str = true;
                    fh = true;
                }else{
                    startIndex = start;
                    str = false;
                    fh = false;
                }
                param = true;
            }
            if(param){
                if(t == 0&&(compare(c,',')||compare(c,')'))){
                    if(compare(pri,'\'')&&fh){
                        endIndex = start-1;
                        String v = values.substring(startIndex,endIndex);
                        params.add(v);
                        param = false;
                    }else if(notCompare(pri,'\'')&&!fh){
                        endIndex = start;
                        String v = values.substring(startIndex,endIndex);
                        params.add(v);
                        param = false;
                    }
                }
                if(compare(c,'(')&&!str){
                    t++;
                }
                if(compare(c,')')&&!str&&t>0){
                    t--;
                }
            }
            start++;
            if(start >= values.length()){
                break;
            }
        }
        return params;
    }

    private static boolean compare(char c,char c1){
        return c == c1;
    }

    private static boolean notCompare(char c,char c1){
        return c != c1;
    }

    public static Map<String, Object> insertMap(String insertSQL){
        Map<String, Object> map = new HashMap<>(4);
        String[] str = SqlUtil.columns(insertSQL);
        List<String> list = SqlUtil.params(insertSQL);
        for(int i=0;i< str.length;i++){
            if(str[i].equals("ctime") || str[i].equals("role") || str[i].equals("departmentCode") || str[i].equals("createName") || str[i].equals("creator")){
                continue;
            }
            map.put(str[i], list.get(i));
        }
        return map;
    }

    public static Map<String,Object> parseUpdate(String sql){
        Map<String,Object> resultMap = new HashMap<>(8);
        try{

        }catch (Exception e){

        }
        return resultMap;
    }



    public static Map<String, Object> updateMap(String updateSQL){
        Map<String, Object> resultMap = parseUpdate(updateSQL);
        List<Object> field = (List<Object>) resultMap.get("field");
        String where = (String)resultMap.get("where");

        return null;
    }

    public static String getKey(String sql, String type){
        sql = sql.toLowerCase();
        if(type.equals("insert")){
            return sql.split("[(]")[0].split("into")[1].trim();
        }else if(type.equals("update")){
            return sql.split("set")[0].replaceAll("update", "").trim();
        }else{
            return sql.split("where")[0].split("from")[1].trim();
        }
    }


    public static void main(String[] args) {
        String insert = "insert into test (id,status,name,ce,acc) values (29,'P','lll','sxsx','Arferwg')";
        String update = "UPDATE table_name SET column1=value1,column2=value2 WHERE some_column='some_value' AND column1 IN('1','2')";
        String delete = "delete from system_log";
        delete = delete.toLowerCase();

        System.out.println((insertMap(insert)));
    }
}
