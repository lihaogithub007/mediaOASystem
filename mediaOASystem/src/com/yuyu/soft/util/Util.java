package com.yuyu.soft.util;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

public class Util {
    private static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     * 
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }

    /**
     * 随机产生一个N位的数字
     * @return
     */
    public static String generateStringLength(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        char ss = '枯';
        System.out.println(ss);
        StringBuilder sb = new StringBuilder("abcdefg");
        System.out.println("=======" + sb.reverse());
    }

    /**
     * 如果string是数字返回，不是返回0
     * @param string
     * @return
     */
    public static int stringToInt(String string) {
        try {
            return Integer.valueOf(string).intValue();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 在0到number中生成size个数字
     * @param size
     * @param number
     * @return
     */
    public static List<Integer> intRandom(int size, int number) {
        List<Integer> list = new ArrayList<Integer>();
        List<Integer> listNumber = new ArrayList<Integer>();
        for (int i = 0; i < number; i++) {
            listNumber.add(i);
        }
        Random rd1 = new Random();

        for (int i = 0; i < size; i++) {
            int count = rd1.nextInt(listNumber.size());
            list.add(listNumber.get(count));
            listNumber.remove(count);
        }
        return list;
    }

    /** 
     * 功能：写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void LogResult(String name, String sWord) {
        // 该文件存在于和应用服务器 启动文件同一目录下，文件名是alipay log加服务器时间
        try {
            FileWriter writer = new FileWriter(name + ".txt", true);
            writer.write(sWord);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取访问Ip
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 取出页数和每页显示的行号
     * @param request
     * @return
     */
    public static PagerInfo handlerPagerInfo(HttpServletRequest request) {
        try {
            int pageSize = "".equals(nullSafeString(request.getParameter("rows"))) ? Constants.DEFAULT_PAGE_SIZE
                : Integer.parseInt(request.getParameter("rows"));
            int pageIndex = "".equals(nullSafeString(request.getParameter("page"))) ? 1 : Integer
                .parseInt(request.getParameter("page"));

            return new PagerInfo(pageSize, pageIndex);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PagerInfo();
    }

    /**
     * 如果为null，转换为""
     * @param value
     * @return
     */
    public static String nullSafeString(String value) {
        return value == null ? "" : value;
    }

    public static PagerInfo handlerPagerInfoFront(HttpServletRequest request) {
        try {
            int pageSize = "".equals(nullSafeString(request.getParameter("rows"))) ? Constants.DEFAULT_PAGE_SIZE
                : Integer.parseInt(request.getParameter("rows"));
            int pageIndex = "".equals(nullSafeString(request.getParameter("currentPage"))) ? 1
                : Integer.parseInt(request.getParameter("currentPage"));

            return new PagerInfo(pageSize, pageIndex);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PagerInfo();
    }
}
