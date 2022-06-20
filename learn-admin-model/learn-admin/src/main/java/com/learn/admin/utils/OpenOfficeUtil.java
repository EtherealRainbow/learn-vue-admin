package com.learn.admin.utils;

import org.jodconverter.JodConverter;
import org.jodconverter.document.DefaultDocumentFormatRegistry;
import org.jodconverter.document.DocumentFormat;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeException;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * TODO
 *
 * @author lijun
 * @program learn-admin
 * @date 2022/5/26 9:35
 */
public class OpenOfficeUtil {

//    @Resource
//    private DocumentConverter converter;

    private final String OFFICE_HOME = "D:\\Program Files (x86)\\OpenOffice 4";

//    private LocalOfficeManager manager = LocalOfficeManager.builder().officeHome(OFFICE_HOME).install().build();;

    public void toPdf(InputStream inputStream ,ServletOutputStream outputStream,String format) throws Exception {
        LocalOfficeManager manager = LocalOfficeManager.builder().officeHome(OFFICE_HOME).install().build();;
        manager.start();
        try {
            DocumentFormat formatByExtension = DefaultDocumentFormatRegistry.getFormatByExtension(format);
            JodConverter.convert(inputStream).as(formatByExtension).to(outputStream).as(DefaultDocumentFormatRegistry.PDF).execute();
//            JodConverter.convert(new File("C:\\Users\\SCKJ-003\\Desktop\\abc.txt")).
//                    to(new File("C:\\Users\\SCKJ-003\\Desktop\\test.pdf")).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
                outputStream.close();
                manager.stop();
            } catch (OfficeException | IOException e) {
                e.printStackTrace();
            }
        }
    }

//    public static void main(String[] args) {
//        try {
//            //据说jodconverter可以自动寻找转码路径，windows上试了可以，linux上好像没法自动找到路径，会报officehome not set and could not be auto-detected，所以设置转码工具路径
//
//
//            JodConverter.convert(new File("C:\\Users\\SCKJ-003\\Desktop\\abc.txt")).to(new File("C:\\Users\\SCKJ-003\\Desktop\\test.pdf")).execute();
//        } catch (OfficeException e) {
//            e.printStackTrace();
//        }
//    }


}
