package com.learn.admin.service.login.impl;


import com.learn.admin.entity.admin.User;
import com.learn.admin.exception.ServiceException;
import com.learn.admin.service.admin.IUserService;
import com.learn.admin.service.login.ILoginService;
import com.learn.admin.utils.CommonUtils;
import com.learn.admin.utils.MD5Utils;
import com.learn.admin.utils.RedisUtil;
import com.learn.admin.utils.TreeModel;
import com.learn.jwt.entity.TokenProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author lijun
 * @program learn-admin
 * @date 2022/4/27 15:48
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Resource
    private IUserService userService;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public User userLogin(Map<String, Object> params) {
        String userName = (String)params.get("userName");
        String password = (String)params.get("password");
//        String md5password = MD5Utils.string2MD5(password);
        try {
            User user = userService.getUserInfo(userName,password);
            if(user.getPassword().equals(password)){
                String token = TokenProvider.createToken(user.getUuid(), userName, password);
                redisUtil.set(user.getUsername(),token);
            }
//            String token = JwtUtils.createToken(userName, md5password);
            return user;
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User getUserInfo(String token) throws ServiceException {


        return null;
    }

}
