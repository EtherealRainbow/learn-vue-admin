package com.learn.admin.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.admin.entity.admin.Role;
import com.learn.admin.exception.ServiceException;

import java.util.Set;

/**
 * @author lijun
 */
public interface IRoleService extends IService<Role> {

    /**
     * 根据用户id 查询角色列表
     *
     * @param
     * @return java.util.Set<java.lang.String>
     * @author lijun
     * @date 2022/6/10
     **/
    Set<String> getRolesByUserId(String userId)throws ServiceException;


}
