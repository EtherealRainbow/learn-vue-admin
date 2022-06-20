package com.learn.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.learn.admin.common.constant.SystemConstant;
import com.learn.admin.utils.DateUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公用控制器
 *
 * @author lijun
 * @date 2021/3/24 17:46
 */
public class CommonController {

    @Resource
    protected HttpServletRequest request;

    @Resource
    protected HttpServletResponse response;

    public String getCurrentDate() {
        return DateUtil.dateToString(new Date(), SystemConstant.DATE_PATTEN);
    }

    public Object outSuccess(Object msg) {
        Map<String, Object> result = new HashMap<>(2);
        result.put("success", true);
        result.put("msg", msg);
        return result;
    }

    public Object outError(Object msg) {
        Map<String, Object> result = new HashMap<>(2);
        result.put("success", false);
        result.put("msg", msg);
        return result;
    }

    public Object layuiUtil(List<?> list) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("msg", "查询成功");
        map.put("success",true);
        map.put("code", 0);
        map.put("count", list == null ? 0 : list.size());
        map.put("data", list);
        return map;
    }

    public Object layuiUtil(IPage<?> page) {

        Map<String, Object> map = new HashMap<>(8);
        map.put("msg", "查询成功");
        map.put("success",true);
        map.put("code", 0);
        map.put("count", page == null ? 0 : page.getSize());
        assert page != null;
        map.put("data", page.getRecords());
        return map;
    }

    /**
     * @author lijun
     * @date 2020/4/25
     **/
    public Object layuiUtil(String msg, String code) {
        Map<String, Object> map = new HashMap<String, Object>(8);
        map.put("msg", msg);
        map.put("code", code);
        map.put("count", null);
        map.put("data", null);
        return map;
    }


}
