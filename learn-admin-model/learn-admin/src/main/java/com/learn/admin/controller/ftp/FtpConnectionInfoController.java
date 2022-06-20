package com.learn.admin.controller.ftp;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.admin.controller.CommonController;
import com.learn.admin.entity.ftp.FtpConnectionInfo;
import com.learn.admin.exception.ServiceException;
import com.learn.admin.service.ftp.IFtpConnectionInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author lijun
 * @program learn-admin
 * @date 2022/5/19 9:36
 */
@RestController
@RequestMapping("ftpConnectionInfo")
public class FtpConnectionInfoController extends CommonController {

    @Resource
    private IFtpConnectionInfoService ftpConnectionInfoService;


//    @RateLimiter(limit = 3, timeout = 500)
    @RequestMapping("getFtpConnectionInfoList")
    public Object getFtpConnectionInfoList(String start,String limit,String systemName){
        Page<FtpConnectionInfo> ftpConnectionInfoList = this.ftpConnectionInfoService.getFtpConnectionInfoList(start, limit, systemName);
        return ftpConnectionInfoList;
    }

    @RequestMapping("addFtpConnectionInfo")
    public Object addFtpConnectionInfo(FtpConnectionInfo o){
        try{
            this.ftpConnectionInfoService.addFtpConnectionInfo(o);
            return this.outSuccess("操作成功!");
        }catch (ServiceException e){
            return this.outError("操作失败!");
        }
    }

    @RequestMapping("updateFtpConnectionInfo")
    public Object updateFtpConnectionInfo(FtpConnectionInfo o){
        try{
            this.ftpConnectionInfoService.updateFtpConnectionInfo(o);
            return this.outSuccess("操作成功!");
        }catch (ServiceException e){
            return this.outError(e.getMessageKey());
        }
    }

    @RequestMapping("deleteFtpConnectionInfo")
    public Object deleteFtpConnectionInfo(String uuid){
        try{
            this.ftpConnectionInfoService.deleteFtpConnectionInfo(uuid);
            return this.outSuccess("操作成功!");
        }catch (ServiceException e){
            return this.outError("操作失败!");
        }
    }

}
