package com.learn.admin.service.ftp;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.admin.entity.ftp.FtpConnectionInfo;
import com.learn.admin.exception.ServiceException;

/**
 * @author lijun
 */
public interface IFtpConnectionInfoService extends IService<FtpConnectionInfo> {

    /**
     * 查询ftp链接信息
     *
     * @param
     * @return void
     * @author lijun
     * @date 2022/5/19
     **/
    Page<FtpConnectionInfo> getFtpConnectionInfoList(String start, String limit, String name)throws ServiceException;

    /**
     * 添加ftp链接信息
     *
     * @param
     * @return void
     * @author lijun
     * @date 2022/5/19
     **/
    void addFtpConnectionInfo(FtpConnectionInfo o)throws ServiceException;


    /**
     * 修改链接信息
     *
     * @param
     * @return void
     * @author lijun
     * @date 2022/5/19
     **/
    void updateFtpConnectionInfo(FtpConnectionInfo o)throws ServiceException;

    /**
     * 删除
     *
     * @param
     * @author lijun
     * @date 2022/5/19
     **/
    void deleteFtpConnectionInfo(String uuid)throws ServiceException;
}
