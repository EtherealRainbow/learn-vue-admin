package com.learn.admin.service.redismq.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.admin.dao.redismq.TaskInfoMapper;
import com.learn.admin.entity.redismq.TaskInfo;
import com.learn.admin.service.redismq.IRedisProducerService;
import com.learn.admin.utils.RedisClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author lijun
 * @program learn-admin-model
 * @date 2022/6/21 11:16
 */
@Service
public class RedisProducerServiceImpl extends ServiceImpl<TaskInfoMapper, TaskInfo> implements IRedisProducerService {

    @Resource
    private RedisClient redisClient;

    @Override
    public void sendMessage(String text) {
        TaskInfo taskInfo = this.baseMapper.getTaskInfoRandom();
        Integer taskCount = taskInfo.getTaskCount();
        Integer finishedCount = taskInfo.getFinishedCount();
        if(taskCount > finishedCount){
            TaskInfo ti = new TaskInfo();
            ti.setId(taskInfo.getId());
            ti.setFinishedCount(taskInfo.getFinishedCount()+1);
            this.updateById(ti);
            redisClient.lpush(taskInfo.getTaskId(), taskInfo);
        }

    }

    @Override
    public TaskInfo receiveMessage(String taskId) {
       return (TaskInfo)redisClient.brpop(taskId, 60, TimeUnit.SECONDS);
    }
}
