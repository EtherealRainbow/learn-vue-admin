package com.learn.admin.controller.admin;

import com.learn.admin.controller.CommonController;
import com.learn.admin.exception.ServiceException;
import com.learn.admin.service.admin.IUserService;
import com.learn.jwt.entity.TokenProvider;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author lijun
 * @program learn-admin-model
 * @date 2022/6/15 10:47
 */
@RestController
@RequestMapping("user")
public class UserController extends CommonController {

    @Resource
    private IUserService userService;

    @RequestMapping("getUserList")
    public Object getUserList(String page,String limit){
        try{
//            userService.getUserList();
            return this.outSuccess("操作成功!");
        }catch (ServiceException e){
            return this.outError("操作失败!");
        }
    }



}
