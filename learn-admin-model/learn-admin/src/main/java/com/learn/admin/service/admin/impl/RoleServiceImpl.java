package com.learn.admin.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.admin.dao.admin.RoleMapper;
import com.learn.admin.entity.admin.Role;
import com.learn.admin.exception.ServiceException;
import com.learn.admin.service.admin.IRoleService;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * TODO
 *
 * @author lijun
 * @program learn-admin
 * @date 2022/6/10 15:30
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    /**
     * 根据用户id 查询角色列表
     *
     * @param userId@return java.util.Set<java.lang.String>
     * @author lijun
     * @date 2022/6/10
     **/
    @Override
    public Set<String> getRolesByUserId(String userId) throws ServiceException {
        return null;
    }
}
