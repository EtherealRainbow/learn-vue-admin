package com.learn.admin.controller.admin;

import com.learn.admin.controller.CommonController;
import com.learn.admin.service.admin.IDepartmentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author lijun
 * @program learn-admin-model
 * @date 2022/6/27 10:15
 */
@RestController
@RequestMapping("department")
public class DepartmentController extends CommonController {

    @Resource
    private IDepartmentService departmentService;

}
