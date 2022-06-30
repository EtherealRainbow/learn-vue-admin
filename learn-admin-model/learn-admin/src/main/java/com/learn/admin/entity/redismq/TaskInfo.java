package com.learn.admin.entity.redismq;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * TODO
 *
 * @author lijun
 * @program learn-admin-model
 * @date 2022/6/21 11:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("task_info")
public class TaskInfo extends Model<TaskInfo> {

    private Integer id;
    private String type;
    private Integer state;
    private Integer taskCount;
    private Integer finishedCount;
    private String taskId;



}
