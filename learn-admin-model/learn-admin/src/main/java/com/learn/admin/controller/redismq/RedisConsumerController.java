package com.learn.admin.controller.redismq;

import com.learn.admin.service.redismq.IRedisProducerService;
import com.learn.admin.utils.RedisClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author lijun
 * @program learn-admin-model
 * @date 2022/6/21 10:08
 */
@RestController
@RequestMapping("/consumer")
public class RedisConsumerController {

    @Resource
    private RedisClient redisClient;


    @Resource
    private IRedisProducerService redisProducerService;

    /** 公共配置 */
    private final static String SUCCESS = "success";

    /**
     * 接收消息API
     * @return
     */
    @RequestMapping("/receiveMessage")
    public Object receiveMessage(String taskId) {
        return redisProducerService.receiveMessage(taskId);
    }

}
