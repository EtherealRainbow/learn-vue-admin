package com.learn.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author lijun
 * @program learn-admin-model
 * @date 2022/5/17 15:27
 */
@Slf4j
public class RateLimiterUtil {

    /**
     * 限流策略 ： 限制每秒最多10个请求
     */
    private static final RateLimiter limiter = RateLimiter.create(2.0);

    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static synchronized void rateLimiter(){
        //500毫秒内，没拿到令牌，就直接进入服务降级
        boolean tryAcquire = limiter.tryAcquire(500, TimeUnit.MILLISECONDS);

        if (!tryAcquire) {
            log.warn("进入服务降级，时间{}", LocalDateTime.now().format(dtf));
        }
        log.info("获取令牌成功，时间{}", LocalDateTime.now().format(dtf));
    }


}
