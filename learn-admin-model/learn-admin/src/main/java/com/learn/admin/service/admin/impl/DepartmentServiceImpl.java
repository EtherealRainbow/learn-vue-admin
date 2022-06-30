package com.learn.admin.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.admin.dao.admin.DepartmentMapper;
import com.learn.admin.entity.admin.Department;
import com.learn.admin.service.admin.IDepartmentService;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author lijun
 * @program learn-admin-model
 * @date 2022/6/27 15:37
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {
}
