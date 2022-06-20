package com.learn.admin.service.ftp.impl;

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.admin.dao.ftp.FtpConnectionInfoMapper;
import com.learn.admin.entity.ftp.FtpConnectionInfo;
import com.learn.admin.exception.ServiceException;
import com.learn.admin.service.ftp.IFtpConnectionInfoService;
import com.learn.admin.utils.FtpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ftp根节点下创建文件夹
 *
 * @author lijun
 * @program learn-admin
 * @date 2022/5/19 11:52
 */
@Service("ftpConnectionInfoService")
public class FtpConnectionInfoServiceImpl extends ServiceImpl<FtpConnectionInfoMapper, FtpConnectionInfo> implements IFtpConnectionInfoService {
    /**
     * 查询ftp链接信息
     *
     * @param start
     * @param limit
     * @param name
     * @return void
     * @author lijun
     * @date 2022/5/19
     **/
    @Override
    public Page<FtpConnectionInfo> getFtpConnectionInfoList(String start, String limit, String name)throws ServiceException {
        LambdaQueryWrapper<FtpConnectionInfo> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(name)){
            queryWrapper.like(FtpConnectionInfo::getSystemName,name);
        }
        return this.page(new Page<>(Integer.parseInt(start), Integer.parseInt(limit)), queryWrapper);
    }

    /**
     * 添加ftp链接信息
     *
     * @param o@return void
     * @author lijun
     * @date 2022/5/19
     **/
    @Override
    @DSTransactional
    public void addFtpConnectionInfo(FtpConnectionInfo o) throws ServiceException {
        LambdaQueryWrapper<FtpConnectionInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FtpConnectionInfo::getIpAddress,o.getIpAddress())
                .eq(FtpConnectionInfo::getPortAddress,o.getPortAddress())
                .eq(FtpConnectionInfo::getSystemName,o.getSystemName())
                .eq(FtpConnectionInfo::getPath,o.getPath());
        List<FtpConnectionInfo> list = this.list(queryWrapper);
        if(list == null || list.size() == 0){
            FtpUtil ftp = new FtpUtil(o.getIpAddress(), Integer.parseInt(o.getPortAddress()), o.getUserName(), o.getPassword());
            try {

                ftp.connect();
                String systemName = o.getSystemName();
                ftp.switchDirectory("/"+systemName,true);
                this.save(o);
            } catch (Exception e) {
                throw new ServiceException(e.getMessage());
            }

        }else{
            throw new ServiceException("当前地址已存在!");
        }


    }

    /**
     * 修改链接信息
     *
     * @param o@return void
     * @author lijun
     * @date 2022/5/19
     **/
    @Override
    @DSTransactional
    public void updateFtpConnectionInfo(FtpConnectionInfo o) throws ServiceException {
        FtpUtil ftp = new FtpUtil(o.getIpAddress(), Integer.parseInt(o.getPortAddress()), o.getUserName(), o.getPassword());
        try {
            String systemName = o.getSystemName();
            FtpConnectionInfo fci = this.getById(o.getUuid());
            ftp.connect();

            if(fci == null){
                throw new ServiceException("修改的数据不存在,请联系管理员");
            }else{
                String systemName1 = fci.getSystemName();
                if(!systemName1.equals(systemName)){
                    ftp.rename("/"+systemName1,"/"+systemName);
                    this.updateById(o);
                }else{
                    throw new ServiceException("数据异常，请联系管理员!");
                }

            }
//            ftp.switchDirectory("/"+o.getPath(),false);
//            ftp.rename("/"+o.getPath(),)
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 删除
     *
     * @param uuid@author lijun
     * @date 2022/5/19
     **/
    @Override
    @DSTransactional
    public void deleteFtpConnectionInfo(String uuid) throws ServiceException {
        FtpConnectionInfo o = this.getById(uuid);
        FtpUtil ftp = new FtpUtil(o.getIpAddress(), Integer.parseInt(o.getPortAddress()), o.getUserName(), o.getPassword());
        try {
            ftp.connect();
            String systemName = o.getSystemName();
            ftp.deleteDirectory("/"+systemName);
            this.removeById(uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
