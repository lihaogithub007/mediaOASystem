package com.yuyu.soft.util.converter;

import java.io.File;

public class FileUtil {

    /**
     * 判断是否是file
     * @param path
     * @return
     */
    public static boolean checkFile(String path) {
        File file = new File(path);
        if (!file.isFile()) {
            return false;
        }
        return true;
    }

    /**
     * 获取文件名：如果是文件返回文件名，如果不是文件返回"";
     * @param fileDir
     * @return
     */
    public static String getFileName(String fileDir) {
        if (!checkFile(fileDir)) {
            return "";
        }
        return fileDir.substring((getFilePath(fileDir).length() + 1), fileDir.lastIndexOf("."));
    }

    /**
     * 获取文件扩展名
     * @param fileDir
     * @return
     */
    public static String getFileExtensionName(String fileDir) {
        if (!checkFile(fileDir)) {
            return "";
        }
        return fileDir.substring(fileDir.lastIndexOf(".") + 1, fileDir.length()).toLowerCase();
    }

    /**
     * 获取文件路径：如果是路径直接返回路径，如果是文件，返回文件所在路径
     * @param fileDir
     * @return
     */
    public static String getFilePath(String fileDir) {
        if (!checkFile(fileDir) && !fileDir.endsWith("/")) {
            return fileDir;
        }
        return fileDir.substring(0, fileDir.lastIndexOf("/"));
    }

    /**
     * 获取文件路径：如果文件存在返回路径，否则返回""
     * @param file
     * @return
     */
    public static String getFilePath(File file) {
        if (file.exists()) {
            String tempStr = file.getPath();
            tempStr = tempStr.replaceAll("\\\\", "/");
            return tempStr;
        } else {
            return "";
        }
    }

    /**
     * 创建目录
     * @param fileDir
     * @return
     */
    public static boolean mkdirs(String fileDir) {
        File file = new File(fileDir);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return true;
    }

    public static void main(String[] args) {
        String path = "c:/swf/3.flv";
        String path2 = "c:/swf/";
        //		System.out.println(checkFile(path));
        //		System.out.println(checkFile(path2));
        //		System.out.println(getFilePath(path));
        //		System.out.println(getFilePath(path2));

        System.out.println("path==" + getFileName(path));
        System.out.println("path2==" + getFileName(path2));
        System.out.println("".equals(getFileName(path2)));
        System.out.println("path==" + getFileExtensionName(path));
        System.out.println("path2==" + getFileExtensionName(path2));
    }
}
