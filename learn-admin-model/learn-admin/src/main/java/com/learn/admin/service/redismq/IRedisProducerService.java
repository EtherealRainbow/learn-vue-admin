package com.learn.admin.service.redismq;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.admin.entity.redismq.TaskInfo;

/**
 * @author SCKJ-003
 */
public interface IRedisProducerService extends IService<TaskInfo> {
    void sendMessage(String text);

    TaskInfo receiveMessage(String taskId);
}
