package com.learn.admin.utils;

import java.util.*;

/**
 * 公共工具类
 *
 * @author lijun
 * @program learn-admin
 * @date 2022/4/29 10:48
 */
public class CommonUtils {

    /**
     * 交集
     **/
    public static String[] partner(String[] m,String[] n) {
        List<String> rs = new ArrayList<String>();
        // 将较长的数组转换为set
        Set<String> set = new HashSet<String>(Arrays.asList(m.length > n.length ? m : n));
        // 遍历较短的数组，实现最少循环
        for (String i : m.length > n.length ? n : m)
        {
            if (set.contains(i))
            {
                rs.add(i);
            }
        }
        String[] arr = {};
        return rs.toArray(arr);
    }

    /**
     * 差集
     **/
    public static String[] getC(String[] m, String[] n)
    {
        // 将较长的数组转换为set
        Set<String> set = new HashSet<String>(Arrays.asList(m.length > n.length ? m : n));

        // 遍历较短的数组，实现最少循环
        for (String i : m.length > n.length ? n : m)
        {
            // 如果集合里有相同的就删掉，如果没有就将值添加到集合
            if (set.contains(i))
            {
                set.remove(i);
            } else
            {
                set.add(i);
            }
        }
        String[] arr = {};
        return set.toArray(arr);
    }

    /**
     * 并集
     **/
    public static String[] getB(String[] m, String[] n)
    {
        // 将数组转换为set集合
        Set<String> set1 = new HashSet<String>(Arrays.asList(m));
        Set<String> set2 = new HashSet<String>(Arrays.asList(n));
        // 合并两个集合
        set1.addAll(set2);
        String[] arr = {};
        return set1.toArray(arr);
    }



}
