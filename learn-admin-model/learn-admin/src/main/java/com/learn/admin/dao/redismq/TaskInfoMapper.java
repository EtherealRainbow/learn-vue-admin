package com.learn.admin.dao.redismq;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.admin.entity.redismq.TaskInfo;

public interface TaskInfoMapper extends BaseMapper<TaskInfo> {
    TaskInfo getTaskInfoRandom();
}
