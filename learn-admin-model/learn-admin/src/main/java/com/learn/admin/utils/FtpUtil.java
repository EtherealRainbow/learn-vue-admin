package com.learn.admin.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author lijun
 */
public class FtpUtil {

    /**
     * 连接FTP Server
     *
     * @throws IOException
     */
    public static final String ANONYMOUS_USER = "anonymous";
    private String Control_Encoding = "GBK";

    private FTPClient client = null;

    private String host = "";
    private int port = 21;
    private String user = "";
    private String password = "";
    private String ftpPath = "/";


    @SuppressWarnings("unused")
    private FtpUtil() {
    }

    public FtpUtil(String host, int port, String user, String pwd) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = pwd;
    }


    public static void main(String[] args) {

        FtpUtil ftp = new FtpUtil("192.168.1.201", 21, "sckj", "win2012-password");
        try{
            // 连接FTP
            ftp.connect();
            ftp.switchDirectory("/FTP/1221111/",false);
            InputStream inputStream = ftp.getInputStream("新建文本文档.txt");
            System.out.println(inputStream);

//            ftp.rename("/FTPA","/FTPAB");
            List<FTPFile> list = ftp.list();
            for (FTPFile ftpFile : list) {
                System.out.println(ftpFile.getName());
            }
//            ftp.changeFilePath("/JWPD/123", "/JWPD/456","pom.xml");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ftp.close();
        }

    }


    /**
     * 获取当前FTP所在目录位置
     *
     * @return
     */
    public String getHome() {
        return this.ftpPath;
    }


    public boolean testConnect(String host,Integer port)throws Exception {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(host, port);
        String status = ftpClient.getStatus();
        System.out.println(status);
        return true;
    }

    public void connect() throws Exception {
        if (client == null) {
            client = new FTPClient();
        }
        // 已经连接
        if (client.isConnected()) {
            return;
        }

        // 编码
        client.setControlEncoding(Control_Encoding);
        try {
            // 连接FTP Server
            client.connect(this.host, this.port);
            // 登陆
            if (this.user == null || "".equals(this.user)) {
                // 使用匿名登陆
                client.login(ANONYMOUS_USER, ANONYMOUS_USER);
            } else {
                client.login(this.user, this.password);
            }
            // 设置文件格式
            client.setFileType(FTPClient.BINARY_FILE_TYPE);
            // 获取FTP Server 应答
            int reply = client.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                client.disconnect();
                throw new Exception("connection FTP fail!");
            }

            FTPClientConfig config = new FTPClientConfig(client.getSystemType().split(" ")[0]);
            config.setServerLanguageCode("zh");
            client.configure(config);
            // 使用被动模式设为默认
            client.enterLocalPassiveMode();
            // 二进制文件支持
            client.setFileType( FTP.BINARY_FILE_TYPE);
            // 设置模式
            client.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);

        } catch (IOException e) {
            throw new Exception("connection FTP fail! " + e);
        }
    }

    /**
     * 断开FTP连接
     *
     * @throws IOException
     */
    public void close() {
        if (client != null && client.isConnected()) {
            try {
                client.logout();
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文件列表
     *
     * @return
     */
    public List<FTPFile> list() {
        List<FTPFile> list = null;
        try {
            FTPFile ff[] = client.listFiles(ftpPath);
            if (ff != null && ff.length > 0) {
                list = new ArrayList<FTPFile>(ff.length);
                Collections.addAll(list, ff);
            } else {
                list = new ArrayList<FTPFile>(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 切换目录
     *
     * @param path
     *            需要切换的目录
     * @param forcedIncrease
     *            如果目录不存在，是否增加
     */
    public void switchDirectory(String path, boolean forcedIncrease) {
        try {
            if (path != null && !"".equals(path)) {
                boolean ok = client.changeWorkingDirectory(path);
                if (ok) {
                    this.ftpPath = path;
                } else if (forcedIncrease) {
                    // ftpPath 不存在，手动创建目录
                    StringTokenizer token = new StringTokenizer(path, "\\//");
                    while (token.hasMoreTokens()) {
                        String npath = token.nextToken();
                        client.makeDirectory(npath);
                        client.changeWorkingDirectory(npath);
                        if (ok) {
                            this.ftpPath = path;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建目录
     *
     * @param path
     */
    public void createDirectory(String path) {
        try {
            if (path != null && !"".equals(path)) {
                boolean ok = client.changeWorkingDirectory(path);
                if (!ok) {
                    // ftpPath 不存在，手动创建目录
                    StringTokenizer token = new StringTokenizer(path, "\\//");
                    while (token.hasMoreTokens()) {
                        String npath = token.nextToken();
                        client.makeDirectory(npath);
                        client.changeWorkingDirectory(npath);
                    }
                }
            }
            client.changeWorkingDirectory(this.ftpPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除目录，如果目录中存在文件或者文件夹则删除失败
     *
     * @param path
     * @return
     */
    public boolean deleteDirectory(String path) {
        boolean flag = false;
        try {
            flag = client.removeDirectory(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除文件
     *
     * @param path
     * @return
     */
    public boolean deleteFile(String path) {
        boolean flag = false;
        try {
            flag = client.deleteFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 上传文件，上传文件只会传到当前所在目录
     *
     * @param localFile
     *            本地文件
     * @return
     */
    public boolean upload(File localFile) {
        return this.upload(localFile, "");
    }

    /**
     * 上传文件，会覆盖FTP上原有文件
     *
     * @param localFile
     *            本地文件
     * @param reName
     *            重名
     * @return
     */
    public boolean upload(File localFile, String reName) {
        boolean flag = false;
        String targetName = reName;
        // 设置上传后文件名
        if (reName == null || "".equals(reName)) {
            targetName = localFile.getName();
        }
        FileInputStream fis = null;
        try {
            // 开始上传文件
            fis = new FileInputStream(localFile);
            client.setControlEncoding(Control_Encoding);
            client.setFileType(FTPClient.BINARY_FILE_TYPE);
            boolean ok = client.storeFile(targetName, fis);
            if (ok) {
                flag = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean upload(InputStream inputStream,String fileName){
        boolean success = false;
        try{
            int reply;
            int replyCode = client.getReplyCode();
            if(!FTPReply.isPositiveCompletion(replyCode)){
                client.disconnect();
                return success;
            }

            client.setControlEncoding(Control_Encoding);
            client.setFileType(FTPClient.BINARY_FILE_TYPE);
            client.enterLocalPassiveMode();
            boolean flag = client.changeWorkingDirectory(ftpPath);
            if(flag == false){
                client.makeDirectory(ftpPath);
                client.changeWorkingDirectory(ftpPath);
            }
            client.storeFile(fileName,inputStream);
            success = true;
        }catch (IOException e){
            success = false;
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                if(client.isConnected()){
                    try {
                        client.disconnect();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
        return success;
    }

    /**
     * 重命名
     *
     * @param formName 需要修改的文件名称(带路径)
     * @param updateName 修改过后的文件名称(带路径)
     * @return boolean
     * @author lijun
     * @date 2021/4/27
     **/
    public boolean rename(String formName,String updateName){
        try {
            return client.rename(formName, updateName);
        } catch (IOException e) {
            e.printStackTrace();
            return false;

        }
    }

    /**
     * 下载文件，如果存在会覆盖原文件
     *
     * @param ftpFileName
     *            文件名称，FTP上的文件名称
     * @param savePath
     *            保存目录，本地保存目录
     * @return
     */
    public boolean download(String ftpFileName, String savePath) {
        boolean flag = false;

        File dir = new File(savePath);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        FileOutputStream fos = null;
        try {
            String saveFile = dir.getAbsolutePath() + File.separator + ftpFileName;
            fos = new FileOutputStream(new File(saveFile));
            boolean ok = client.retrieveFile(ftpFileName, fos);
            if (ok) {
                client.logout();
                flag = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 流下载
     *
     * @param fileDir 服务器文件路径
     * @param fileName 文件名称
     * @param response
     * @return void
     * @author lijun
     * @date 2021/4/28
     **/
    public void download(String fileDir,String fileName, HttpServletResponse response){

        InputStream is = null;
        BufferedInputStream  bis = null;
        try {
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
            client.enterLocalPassiveMode();
            int replyCode = client.getReplyCode();
            client.changeWorkingDirectory(fileDir);
            if(!FTPReply.isPositiveCompletion(replyCode)){
                client.disconnect();
                throw new IOException("FTP连接失败!");
            }
            is = client.retrieveFileStream(fileName);
            bis = new BufferedInputStream(is);
            ServletOutputStream outputStream = response.getOutputStream();
            writeFile(outputStream,is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
            try {
                if(bis != null){
                    bis.close();
                }
                if(is != null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void writeFile(OutputStream fos, InputStream fis) throws IOException{
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = fis.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
            fos.flush();
        }
        fis.close();
        fos.close();
    }

    public InputStream getInputStream(String systemName){
        try {
            return client.retrieveFileStream(systemName);
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }
    }

    /**
     * 复制粘贴
     *
     * @param filePath 当前文件路径
     * @param newPath 目标文件路径
     * @param fileName 文件名称
     * @return boolean
     * @author lijun
     * @date 2021/5/17
     **/
    public boolean changeFilePath(String filePath,String newPath,String fileName){
        boolean result = false;
        if(StringUtils.isEmpty(filePath) ){
            return result;
        }
        try{
            String currentFile = filePath+"/"+fileName;
            String targetFile = newPath+"/"+fileName;
            client.rename(currentFile,targetFile);
            result = true;
        }catch (Exception e){
            result = false;
        }

        return result;
    }

}
