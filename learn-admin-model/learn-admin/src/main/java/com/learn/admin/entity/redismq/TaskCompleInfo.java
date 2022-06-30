package com.learn.admin.entity.redismq;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * TODO
 *
 * @author lijun
 * @program learn-admin-model
 * @date 2022/6/21 11:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("task_info")
public class TaskCompleInfo extends Model<TaskCompleInfo> {

    private String taskId;
    private String file_name;
    private String username;
    private String realname;
    private String createBy;
    private Date createTime;
    private String updateBy;
    private Date updateTime;
    private Integer id;
    private Integer pid;


}
