package com.yuyu.soft.util.converter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

public class ConverterOfficeToSWF {

    protected Logger log = LoggerFactory.getLogger(ConverterOfficeToSWF.class);

    private String   fileName;
    private File     pdfFile;
    private File     officeFile;

    private void init(String fileDir, String savePath) {

        if (!savePath.endsWith("/")) {
            savePath = savePath + "/";
        }
        if (!FileUtil.mkdirs(savePath)) {
            throw new RuntimeException(ConverterConst.ERROR_MKDIR_SAVE_PATH);
        }
        fileName = FileUtil.getFileName(fileDir);
        officeFile = new File(fileDir);
        pdfFile = new File(savePath + fileName + ".pdf");
    }

    /**
     * 转换主方法
     * @return
     * @throws Exception 
     */
    public String converter(String fileDir, String savePath) throws Exception {
        init(fileDir, savePath);
        if (isWindowsSystem()) {
            log.info("\n 【office转pdf】****pdf转换器开始工作，当前设置运行环境windows****");
        } else {
            log.info("\n 【office转pdf】****pdf转换器开始工作，当前设置运行环境linux****");
        }
        office2pdf();
        return FileUtil.getFilePath(pdfFile);
    }

    /**
     * 转为PDF
     */
    private void office2pdf() throws Exception {
        if (officeFile.exists()) {
            if (!pdfFile.exists()) {
                OpenOfficeConnection connection = new SocketOpenOfficeConnection(
                    ConverterConst.OPENOFFICE_PORT);
                try {
                    connection.connect();
                    DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
                    converter.convert(officeFile, pdfFile);
                    connection.disconnect(); //close the connection
                    log.info("\n 【office转pdf】****pdf转换成功，PDF输出：" + pdfFile.getPath() + "****");
                } catch (ConnectException e) {
                    e.printStackTrace();
                    throw new RuntimeException("服务器未安装openoffice或openoffice服务未启动！");
                } catch (OpenOfficeException e) {
                    e.printStackTrace();
                    throw new RuntimeException("预览文件失败");
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("预览文件失败");
                }
            } else {
                log.info("\n 【office转pdf】****已经转换为pdf，不需要再进行转化****");
            }
        } else {
            throw new RuntimeException("文档不存在，无法预览");
        }
    }

    static String loadStream(InputStream in) throws IOException {
        int ptr = 0;
        in = new BufferedInputStream(in);
        StringBuffer buffer = new StringBuffer();
        while ((ptr = in.read()) != -1) {
            buffer.append((char) ptr);
        }
        return buffer.toString();
    }

    /**
     * 判断是否是windows操作系统
     */
    private static boolean isWindowsSystem() {
        String p = System.getProperty("os.name");
        return p.toLowerCase().indexOf("windows") >= 0 ? true : false;
    }

    /**
     * 测试
     * @throws Exception 
     */
    public static void main(String args[]) {

        String fileDir = "D:/LM/office/hibernate笔记.docx";
        String savePath = "D:/LM/office/";
        ConverterOfficeToSWF d = new ConverterOfficeToSWF();
        try {
            System.out.println(d.converter(fileDir, savePath));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
