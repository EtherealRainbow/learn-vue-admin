package com.learn.admin.controller;

import com.learn.admin.entity.admin.User;
import com.learn.admin.exception.ServiceException;
import com.learn.admin.service.admin.IUserService;
import com.learn.admin.service.login.ILoginService;
import com.learn.admin.utils.result.Result;
import com.learn.jwt.config.PasswordEncoder;
import com.learn.jwt.entity.JwtUser;
import com.learn.jwt.entity.TokenProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录类
 *
 * @author lijun
 * @program learn
 * @date 2021/3/22 11:17
 */
@RestController
public class LoginController extends CommonController{

    @Resource
    private IUserService userService;

    /**
     * 登录
     *
     * @param
     * @return java.lang.Object
     * @author lijun
     * @date 2022/6/14
     **/
    @RequestMapping(value = "userLogin")
    public Object userLogin(@RequestBody Map<String,Object> params){
        try{
            String username = (String)params.get("userName");
            String password = (String)params.get("password");
            User user = userService.getUserByUserName(username);
            boolean matches = PasswordEncoder.matches(password, user.getPassword());
            if(matches){
                String token = TokenProvider.createToken(user.getUuid(), username, password);
                response.setHeader(TokenProvider.TOKEN_HEADER, TokenProvider.TOKEN_PREFIX+token);
                return new Result<User>().data(user).code(200).message(token);
            }else{
                throw new ServiceException("密码错误");
            }
        }catch (ServiceException e){
            return this.outError(e.getMessage());
        }
    }

    @GetMapping(value = "logout")
    public Object logout(String username,String password){
        return "/views/welcome";
    }

    @RequestMapping(value = "getUserInfoByToken")
    public Object getUserInfoByToken(String token){
        return TokenProvider.checkToken(token);
    }

}
