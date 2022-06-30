package com.learn.admin.controller.redismq;

import com.learn.admin.service.redismq.IRedisProducerService;
import com.learn.admin.utils.RedisClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * TODO
 *
 * @author lijun
 * @program learn-admin-model
 * @date 2022/6/21 10:07
 */

@RestController
@RequestMapping("/producer")
public class RedisProducerController {



    @Resource
    private IRedisProducerService redisProducerService;

    /** 公共配置 */
    private final static String SUCCESS = "success";
    private final static String MESSAGE = "testmq";
    /**
     * 消息发送API
     * @return
     */
    @RequestMapping("/sendMessage")
    public String sendMessage(String text) {
        redisProducerService.sendMessage(text);

        return SUCCESS;
    }


}
