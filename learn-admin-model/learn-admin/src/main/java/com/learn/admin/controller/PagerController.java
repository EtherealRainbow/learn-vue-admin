package com.learn.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 页面转发类
 *
 * @author lijun
 * @program learn
 * @date 2021/3/24 17:21
 */
@Controller
@RequestMapping(value="/pages")
public class PagerController {

    @Resource
    protected HttpServletRequest request;

    @Resource
    protected HttpServletResponse response;

    private void setModelMap(HttpServletRequest req,ModelMap modelMap){
        Map<String,String[]> map = req.getParameterMap();
        Set<String> key = map.keySet();

        for (Iterator<String> iterator = key.iterator();iterator.hasNext();){
            String s = iterator.next();
            modelMap.put( s,map.get( s )[0] );
        }
    }

    @RequestMapping(value="{path1}",method= RequestMethod.GET)
    public String page(HttpServletRequest req,ModelMap modelMap, @PathVariable("path1") String path1){
        this.setModelMap(req, modelMap );
        String path ="/"+path1;
        return path;
    }

    @RequestMapping(value="{path1}/{path2}",method= RequestMethod.GET)
    public String page(HttpServletRequest req,ModelMap modelMap, @PathVariable("path1") String path1,
                       @PathVariable("path2") String path2){
        this.setModelMap(req, modelMap );
        String path = "/"+path1+"/"+path2;
        return path;
    }


    @RequestMapping(value="{path1}/{path2}/{path3}",method= RequestMethod.GET)
    public String page(HttpServletRequest req,ModelMap modelMap,@PathVariable("path1") String path1,@PathVariable("path2") String path2
            ,@PathVariable("path3") String path3){
        this.setModelMap(req, modelMap );
        String path = "/"+ path1+"/"+path2+"/"+path3;
        return path;
    }

//    @ApiOperation(value = "login",notes = "跳转登录界面")
//    @GetMapping(value = "login" )
//    public String login(){
//        return "login2";
//    }
//
//    @ApiOperation(value = "views/welcome",notes = "跳转欢迎界面")
//    @GetMapping(value = "views/welcome")
//    public String welcome(){
//        return "views/welcome";
//    }
//
//    @ApiOperation(value = "databaseConfig",notes = "跳转数据源配置界面")
//    @GetMapping(value = "databaseConfig")
//    public String databaseConfig(){
//        return "views/config/databaseConfig";
//    }
//
//    @ApiOperation(value = "databaseConfigFrom",notes = "跳转至数据源表单界面")
//    @GetMapping(value = "databaseConfigFrom")
//    public String databaseConfigFrom(){
//        return "views/config/databaseConfigFrom";
//    }
//
//    @ApiOperation(value = "viewDatabaseInfo",notes = "跳转至数据源展示界面")
//    @GetMapping(value = "viewDatabaseInfo")
//    public String viewDatabaseInfo(){
//        return "views/config/viewDatabaseInfo";
//    }
//
//    @ApiOperation(value = "fieldConfigFrom",notes = "跳转至字段表单界面")
//    @GetMapping(value = "fieldConfigFrom")
//    public String fieldConfigFrom(){
//        return "views/config/fieldConfigFrom";
//    }
//
//    @ApiOperation(value = "systemConfig",notes = "跳转至字段表单界面")
//    @GetMapping(value = "systemConfig")
//    public String systemConfig(){
//        return "views/system/systemConfig";
//    }
//
//    @ApiOperation(value = "systemConfigFrom",notes = "跳转至字段表单界面")
//    @GetMapping(value = "systemConfigFrom")
//    public String systemConfigFrom(){
//        return "views/system/systemConfigFrom";
//    }
//
//    @ApiOperation(value = "systemFtp",notes = "文件管理界面")
//    @GetMapping(value = "systemFtp")
//    public String systemFtp(){
//        return "views/system/systemFtp";
//    }
//
//    @ApiOperation(value = "systemFtpFrom",notes = "文件管理创建文件夹")
//    @GetMapping(value = "systemFtpFrom")
//    public String systemFtpFrom(){
//        return "views/system/systemFtpFrom";
//    }

}
