package com.learn.admin.service.ftp.impl;


import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.admin.dao.ftp.FtpFileInfoMapper;
import com.learn.admin.entity.ftp.FtpConnectionInfo;
import com.learn.admin.entity.ftp.FtpFileInfo;
import com.learn.admin.exception.ServiceException;
import com.learn.admin.service.ftp.IFtpConnectionInfoService;
import com.learn.admin.service.ftp.IFtpFileInfoService;
import com.learn.admin.utils.FtpUtil;
import com.learn.admin.utils.OpenOfficeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author lijun
 * @program learn-admin
 * @date 2022/5/20 10:31
 */
@Service("ftpFileInfoService")
public class FtpFileInfoServiceImpl extends ServiceImpl<FtpFileInfoMapper, FtpFileInfo> implements IFtpFileInfoService {

    @Resource
    private IFtpConnectionInfoService ftpConnectionInfoService;

    @Override
    public Page<FtpFileInfo> getFtpFileInfoList(Map<String, String> params) throws ServiceException {
        String parentId = params.get("systemId");
        String path = params.get("path");
        String start = params.get("start");
        String limit = params.get("limit");
        String systemName = params.get("systemName");
        LambdaQueryWrapper<FtpFileInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FtpFileInfo::getParentId,parentId);
        if(StringUtils.isNotBlank(path)){
            queryWrapper.eq(FtpFileInfo::getPath,path);
        }
        if(StringUtils.isNotBlank(systemName)){
            queryWrapper.like(FtpFileInfo::getSystemName,systemName);
        }
        Page<FtpFileInfo> page = this.page(new Page<>(Integer.parseInt(start), Integer.parseInt(limit)), queryWrapper);
        if(page == null){
            return new Page<>();
        }
        return page;
    }

    @Override
    @DSTransactional
    public void addFtpFileInfo(FtpFileInfo ftpFileInfo,String rootUuid) throws ServiceException {
        FtpConnectionInfo systemConfig = this.ftpConnectionInfoService.getById(rootUuid);
        //存储文件上传信息
        FtpUtil ftp = new FtpUtil(systemConfig.getIpAddress(),Integer.parseInt(systemConfig.getPortAddress()),systemConfig.getUserName(),systemConfig.getPassword());
        try {
            LambdaQueryWrapper<FtpFileInfo> query = new LambdaQueryWrapper<>();
            query.eq(FtpFileInfo::getPath,ftpFileInfo.getPath())
                    .eq(FtpFileInfo::getParentId,ftpFileInfo.getParentId())
                    .eq(FtpFileInfo::getSystemName,ftpFileInfo.getSystemName())
                    .eq(FtpFileInfo::getFileType,ftpFileInfo.getFileType());
            List<FtpFileInfo> list = this.list(query);
            if(list == null ||list.size() == 0){
                ftpFileInfo.setPath("/"+ftpFileInfo.getPath());
                String systemName = ftpFileInfo.getSystemName();
                ftpFileInfo.setSuffix(getFileExtension(systemName));
                this.save(ftpFileInfo);
                ftp.connect();
                ftp.switchDirectory("/"+ftpFileInfo.getPath()+ftpFileInfo.getSystemName(),true);
            }else{
                throw new ServiceException("上传的文件数据已存在!");
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void updateFtpFileInfo(FtpFileInfo ftpFileInfo,String rootUuid) throws ServiceException {
        FtpConnectionInfo systemConfig = this.ftpConnectionInfoService.getById(rootUuid);
        //存储文件上传信息
        FtpUtil ftp = new FtpUtil(systemConfig.getIpAddress(),Integer.parseInt(systemConfig.getPortAddress()),systemConfig.getUserName(),systemConfig.getPassword());
        try {
            String systemName = ftpFileInfo.getSystemName();
            String path = ftpFileInfo.getPath()+systemName;
            String uuid = ftpFileInfo.getUuid();
            FtpFileInfo sff = this.getById(uuid);
            if(sff == null ){
                throw new ServiceException("要修改的数据不存在!");
            }else{
                if(sff.getSystemName().equals(ftpFileInfo.getSystemName())){
                    throw new ServiceException("文件夹已存在,请重新录入!");
                }else{
                    ftpFileInfo.setPath("/"+path);
                    this.updateById(ftpFileInfo);
                    ftp.connect();
                    ftp.rename("/"+sff.getPath()+sff.getSystemName(),"/"+path);
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    public void deleteFtpFileInfo(String uuid,String rootUuid,String path) throws ServiceException {
        FtpConnectionInfo systemConfig = this.ftpConnectionInfoService.getById(rootUuid);
        FtpFileInfo ftpFileInfo = this.getById(uuid);
        FtpUtil ftp = new FtpUtil(systemConfig.getIpAddress(),Integer.parseInt(systemConfig.getPortAddress()),systemConfig.getUserName(),systemConfig.getPassword());
        try {
            ftp.connect();
            if("WJJ".equals(ftpFileInfo.getFileType())){
                ftp.deleteDirectory("/"+path+ftpFileInfo.getSystemName());
            }else{
                ftp.deleteFile("/"+path+ftpFileInfo.getSystemName());
            }
            this.removeById(uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    @Override
    public void uploadFiles(MultipartFile file,String uuid, String rootUuid, String path) throws ServiceException {
        FtpConnectionInfo systemConfig = this.ftpConnectionInfoService.getById(rootUuid);
        //存储文件上传信息
        FtpUtil ftp = new FtpUtil(systemConfig.getIpAddress(),Integer.parseInt(systemConfig.getPortAddress()),systemConfig.getUserName(),systemConfig.getPassword());
        try {
            FtpFileInfo ftpFileInfo = new FtpFileInfo();
            ftpFileInfo.setPath("/"+path);
            ftpFileInfo.setSystemName(file.getOriginalFilename());
            ftpFileInfo.setFileSize(file.getSize()+"");
            ftpFileInfo.setFileType("file");
            ftpFileInfo.setParentId(uuid);
//            file.getOriginalFilename().get
            ftpFileInfo.setSuffix(getFileExtension(file.getOriginalFilename()));
            this.save(ftpFileInfo);
            ftp.connect();
            ftp.upload(file.getInputStream(),ftpFileInfo.getPath()+"/"+file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getFileExtension(String fileName){
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".")+1);
        }
        return "";
    }


    @Override
    public void downloadFile(String rootUuid, String uuid,HttpServletResponse response) throws ServiceException {
        //获取FTP服务器链接
        FtpConnectionInfo systemConfig = this.ftpConnectionInfoService.getById(rootUuid);
        FtpFileInfo fileInfo = this.getById(uuid);
        FtpUtil ftp = new FtpUtil(systemConfig.getIpAddress(),Integer.parseInt(systemConfig.getPortAddress()),systemConfig.getUserName(),systemConfig.getPassword());
        try {
            ftp.connect();
//            ftp.switchDirectory(fileInfo.getPath(),false);
            ftp.download(fileInfo.getPath()+fileInfo.getSystemName(),fileInfo.getSystemName(), response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fileDelete(String uuid, String path) throws ServiceException {
        FtpConnectionInfo systemConfig = this.ftpConnectionInfoService.getById(uuid);

        FtpUtil ftp = new FtpUtil(systemConfig.getIpAddress(),Integer.parseInt(systemConfig.getPortAddress()),systemConfig.getUserName(),systemConfig.getPassword());
        try{

            ftp.connect();
            ftp.deleteFile(path);
        } catch (Exception e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void preview(String uuid,String rootUuid,HttpServletResponse response) throws ServiceException {
        FtpConnectionInfo systemConfig = this.ftpConnectionInfoService.getById(rootUuid);
        FtpFileInfo fileInfo = this.getById(uuid);
        FtpUtil ftp = new FtpUtil(systemConfig.getIpAddress(),Integer.parseInt(systemConfig.getPortAddress()),systemConfig.getUserName(),systemConfig.getPassword());
        try {
            ftp.connect();
            ftp.switchDirectory(fileInfo.getPath(),false);
            InputStream inputStream = ftp.getInputStream(fileInfo.getSystemName());
            ServletOutputStream outputStream = response.getOutputStream();
            OpenOfficeUtil openOfficeUtil = new OpenOfficeUtil();
            openOfficeUtil.toPdf(inputStream,outputStream,fileInfo.getSuffix() );
        } catch (Exception e) {
            e.printStackTrace();
        }
//
//
//        return path;
    }





}
