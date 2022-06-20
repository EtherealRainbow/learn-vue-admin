package com.learn.admin.controller.ftp;

import com.learn.admin.controller.CommonController;
import com.learn.admin.entity.ftp.FtpFileInfo;
import com.learn.admin.exception.ServiceException;
import com.learn.admin.service.ftp.IFtpFileInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author lijun
 * @program learn-admin
 * @date 2022/5/20 10:30
 */
@RestController
@RequestMapping("ftpFileInfo")
public class FtpFileInfoController extends CommonController {


    @Resource
    private IFtpFileInfoService ftpFileInfoService;

    @RequestMapping("getFtpFileInfoList")
    public Object getFtpFileInfoList(String systemId,String path,String start,String limit,String systemName){
        try{
            Map<String,String> params = new HashMap<>(8);
            params.put("systemId",systemId);
            params.put("path",path);
            params.put("start",start);
            params.put("limit",limit);
            params.put("systemName",systemName);
            return ftpFileInfoService.getFtpFileInfoList(params);
        }catch (ServiceException e){
            return this.outError("操作失败!");
        }
    }

    @RequestMapping("addFtpFileInfo")
    public Object addFtpFileInfo(FtpFileInfo ftpFileInfo,String rootUuid){
        try{
            ftpFileInfo.setCreateDate(this.getCurrentDate());
            this.ftpFileInfoService.addFtpFileInfo(ftpFileInfo,rootUuid);
            return this.outSuccess("操作成功!");
        }catch (ServiceException e){
            return this.outError("操作失败!");
        }
    }

    @RequestMapping("updateFtpFileInfo")
    public Object updateFtpFileInfo(FtpFileInfo ftpFileInfo,String rootUuid){
        try{
            this.ftpFileInfoService.updateFtpFileInfo(ftpFileInfo,rootUuid);
            return this.outSuccess("操作成功!");
        }catch (ServiceException e){
            return this.outError("操作失败!");
        }
    }

    @RequestMapping("deleteFtpFileInfo")
    public Object deleteFtpFileInfo(String uuid,String rootUuid,String path){
        try{
            this.ftpFileInfoService.deleteFtpFileInfo(uuid,rootUuid,path);
            return this.outSuccess("操作成功!");
        }catch (ServiceException e){
            return this.outError("操作失败!");
        }
    }

    /**
     *
     *
     * @param file 文件流
     * @param uuid 系统uuid
     * @param path 当前路径
     * @return java.lang.Object
     * @author lijun
     * @date 2022/5/23
     **/
    @RequestMapping("uploadFiles")
    public Object uploadFiles(@RequestParam("file") MultipartFile file,String uuid, String rootUuid, String path){

        try{
            // 获取文件名
            this.ftpFileInfoService.uploadFiles(file,uuid,rootUuid,path);


//            ftp.connect();
//            ftp.switchDirectory(path,false);
//            for (MultipartFile file : files) {
//                String originalFilename = file.getOriginalFilename();
//                InputStream inputStream = file.getInputStream();
//                ftp.upload(inputStream,originalFilename);
//
//            }
            return this.outSuccess("上传成功！");
        } catch (Exception e){
            e.printStackTrace();
            return this.outError("上传失败!");
        }
    }


    /**
     *
     *
     * @param uuid 文件UUID
     * @return void
     * @author lijun
     * @date 2022/5/23
     **/
    @RequestMapping("downloadFile")
    public void downloadFile(String rootUuid,String uuid) {
        this.ftpFileInfoService.downloadFile(rootUuid,uuid,response);
    }

    /**
     * 文件删除
     *
     * @param
     * @return java.lang.Object
     * @author lijun
     * @date 2022/5/24
     **/
    @RequestMapping("fileDelete")
    public Object fileDelete(String uuid,String path){
       try{
           this.ftpFileInfoService.fileDelete(uuid,path);
           return this.outSuccess("删除成功");
       }catch (ServiceException e){
           return this.outError("删除失败!");
       }
    }

    @RequestMapping("preview")
    public void preview(@RequestParam("uuid") String uuid,String rootUuid) throws Exception{
        ftpFileInfoService.preview(uuid,rootUuid,response);
    }


}
