package com.learn.admin.controller;

import com.learn.ratelimiter.RateLimiterUtil;
import com.learn.admin.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * 测试限流
 *
 * @author lijun
 * @program learn-admin
 * @date 2022/5/17 9:51
 */
@Slf4j
@RestController
@RequestMapping("test")
public class TestController extends CommonController {

    @RequestMapping("testLimiter")
    public Object testLimiter(){
        try{
            //500毫秒内，没拿到令牌，就直接进入服务降级
            RateLimiterUtil.rateLimiter();
            return this.outSuccess("请求成功");
        }catch (ServiceException e){
            return this.outError("操作失败!");
        }
    }

}
