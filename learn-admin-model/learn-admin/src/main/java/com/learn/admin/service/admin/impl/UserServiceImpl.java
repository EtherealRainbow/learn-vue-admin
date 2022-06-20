package com.learn.admin.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.admin.dao.admin.UserMapper;
import com.learn.admin.entity.admin.User;
import com.learn.admin.exception.ServiceException;
import com.learn.admin.service.admin.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 *
 * @author lijun
 * @program learn-admin
 * @date 2022/6/6 16:27
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    /**
     * 根据用户名密码查询用户信息
     *
     * @param userName 用户名
     * @param password 密码
     * @return com.learn.vue.entity.admin.User
     * @author lijun
     * @date 2022/6/6
     **/
    @Override
    public User getUserInfo(String userName, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,userName);
        User user = this.getOne(queryWrapper);
        if(user == null){
            throw new ServiceException("账号不存在!");
        }
        return user;
    }

    /**
     * 根据用户名称 查询用户信息
     *
     * @param userName 用户名
     * @return com.learn.vue.entity.admin.User
     * @author lijun
     * @date 2022/6/10
     **/
    @Override
    public User getUserByUserName(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,userName);
        return this.getOne(queryWrapper);
    }
}
