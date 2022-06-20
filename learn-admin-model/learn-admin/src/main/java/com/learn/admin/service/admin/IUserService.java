package com.learn.admin.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.admin.entity.admin.User;

/**
 * @author lijun
 */
public interface IUserService extends IService<User> {
    /**
     * 根据用户名密码查询用户信息
     *
     * @param userName 用户名
     * @param password 密码
     * @return com.learn.vue.entity.admin.User
     * @author lijun
     * @date 2022/6/6
     **/
    User getUserInfo(String userName, String password);

    /**
     * 根据用户名称 查询用户信息
     *
     * @param userName 用户名
     * @return com.learn.vue.entity.admin.User
     * @author lijun
     * @date 2022/6/10
     **/
    User getUserByUserName(String userName);


}
