package com.learn.admin.common.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @author lijun
 * @program learn
 * @date 2021/3/22 11:25
 */
public class LoginHandleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
//        try{
//            boolean loginSuccess = request.getSession().getAttribute("person") == null;
//            if(loginSuccess){
//                return true;
//            }else{
//                request.setAttribute("loginMessage","请先登录");
//                request.getRequestDispatcher("/login.html").forward(request,response);
//                return false;
//            }
//
//        }catch (Exception e){
//            return false;
//        }
    }


}
