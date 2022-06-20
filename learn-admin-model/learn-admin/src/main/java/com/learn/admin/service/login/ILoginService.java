package com.learn.admin.service.login;

import com.learn.admin.entity.admin.User;
import com.learn.admin.exception.ServiceException;
import com.learn.admin.utils.TreeModel;

import java.util.List;
import java.util.Map;

/**
 * @author lijun
 */
public interface ILoginService {

    /**
     * 登录接口
     *
     * @param params 登录参数
     * <table>
     *  <tr>
     *   <td>username：</td>
     *   <td>用户名</td>
     *  </tr>
     *  <tr>
     *   <td>password：</td>
     *   <td>密码</td>
     *  </tr>
     * </table>
     * @author lijun
     * @date 2022/6/6
     **/
    User userLogin(Map<String, Object> params)throws ServiceException;

    User getUserInfo(String params)throws ServiceException;
}
