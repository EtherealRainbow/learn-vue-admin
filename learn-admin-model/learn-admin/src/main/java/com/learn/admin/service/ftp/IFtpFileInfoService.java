package com.learn.admin.service.ftp;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.admin.entity.ftp.FtpFileInfo;
import com.learn.admin.exception.ServiceException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author lijun
 */
public interface IFtpFileInfoService extends IService<FtpFileInfo> {


    Page<FtpFileInfo> getFtpFileInfoList(Map<String, String> params)throws ServiceException;

    void addFtpFileInfo(FtpFileInfo ftpFileInfo,String rootUuid)throws ServiceException;

    void updateFtpFileInfo(FtpFileInfo ftpFileInfo,String rootUuid)throws ServiceException;

    void deleteFtpFileInfo(String uuid,String rootUuid,String path)throws ServiceException;

    void uploadFiles(MultipartFile file, String uuid, String rootUuid, String path)throws ServiceException;

    void downloadFile(String rootUuid, String uuid,HttpServletResponse response)throws ServiceException;

    void fileDelete(String uuid, String path)throws ServiceException;

    void preview(String uuid,String rootUuid, HttpServletResponse response)throws ServiceException;
}
