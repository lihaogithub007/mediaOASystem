package com.yuyu.soft.util;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.yuyu.soft.base.mv.JModelAndView;

/**工具类
 *                       
 * @Filename: CommUtil.java
 * @Version: 1.0
 * @Author: 范光洲
 * @Email: goodfgz@163.com
 *
 */
public class CommUtil {

    /**数组转String
     * @param strs
     * @param prefix
     * @return
     */
    public static String arrToString(String[] strs, String prefix) {
        String str = null;
        if (strs != null && strs.length > 0) {
            str = "";
            for (String string : strs) {
                str += string + prefix;
            }
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**首字母小写
     * @param str
     * @return
     */
    public static String first2low(String str) {
        String s = "";
        s = str.substring(0, 1).toLowerCase() + str.substring(1);
        return s;
    }

    /**首字母大写
     * @param str
     * @return
     */
    public static String first2upper(String str) {
        String s = "";
        s = str.substring(0, 1).toUpperCase() + str.substring(1);
        return s;
    }

    public static List<String> str2list(String s) throws IOException {
        List<String> list = new ArrayList<String>();
        if ((s != null) && (!s.equals(""))) {
            StringReader fr = new StringReader(s);
            BufferedReader br = new BufferedReader(fr);
            String aline = "";
            while ((aline = br.readLine()) != null) {
                list.add(aline);
            }
        }
        return list;
    }

    public static Date formatDate(String s) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date d = null;
        try {
            d = dateFormat.parse(s);
        } catch (Exception localException) {
        }
        return d;
    }

    public static Date formatDate(String s, String format) {
        Date d = null;
        try {
            SimpleDateFormat dFormat = new SimpleDateFormat(format);
            d = dFormat.parse(s);
        } catch (Exception localException) {
        }
        return d;
    }

    public static String formatTime(String format, Object v) {
        if (v == null) {
            return null;
        }
        if (v.equals("")) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(v);
    }

    public static String formatLongDate(Object v) {
        if ((v == null) || (v.equals(""))) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(v);
    }

    public static String formatShortDate(Object v) {
        if (v == null) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(v);
    }

    public static String decode(String s) {
        String ret = s;
        try {
            ret = URLDecoder.decode(s.trim(), "UTF-8");
        } catch (Exception localException) {
        }
        return ret;
    }

    /**字符转码
     * @param s
     * @return
     */
    public static String encode(String s) {
        String ret = s;
        try {
            ret = URLEncoder.encode(s.trim(), "UTF-8");
        } catch (Exception localException) {
        }
        return ret;
    }

    /**字符转码
     * @param str
     * @param coding
     * @return
     */
    public static String convert(String str, String coding) {
        String newStr = "";
        if (str != null) {
            try {
                newStr = new String(str.getBytes("ISO-8859-1"), coding);
            } catch (Exception e) {
                return newStr;
            }
        }
        return newStr;
    }

    /**
     * 判断字符串为null或空
     */
    public static boolean isBlank(String str) {
        if ((str == null) || ("".equals(str.trim())) || ("null".equals(str.trim()))) {
            return true;
        }
        return false;
    }

    /**判断对象不为null和空
     * @param obj
     * @return
     */
    public static boolean isNotNull(Object obj) {
        if ((obj != null) && (!obj.toString().equals(""))
            && (!obj.toString().trim().equalsIgnoreCase("null"))) {
            return true;
        }
        return false;
    }

    /**String截取  超出部分用...
     * @param s
     * @param maxLength
     * @return
     */
    public static String substring(String s, int maxLength) {
        if (!StringUtils.hasLength(s)) {
            return s;
        }
        if (s.length() <= maxLength) {
            return s;
        }
        return s.substring(0, maxLength) + "...";
    }

    /**String截取  从某个位置截取至末尾
     * @param s
     * @param start
     * @return
     */
    public static String substringo2o(String s, int start) {
        if (null != s && !"".equals(s) && s.length() > 0 && s.length() > start) {
            return s.substring(start, s.length());
        } else {
            return "";
        }
    }

    /**String截取  从某个字条截取到末尾
     * @param s
     * @param from
     * @return
     */
    public static String substringfrom(String s, String from) {
        if (s.indexOf(from) < 0) {
            return "";
        }
        return s.substring(s.indexOf(from) + from.length());
    }

    public static BigDecimal null2BigDecimal(Object s) {
        BigDecimal v = new BigDecimal(-1);
        if (s != null) {
            try {
                v = BigDecimal.valueOf(null2Double(s));
            } catch (Exception localException) {
            }
        }
        return v;
    }

    /**将null转为int
     * @param s
     * @return
     */
    public static int null2Int(Object s) {
        int v = 0;
        if (s != null) {
            try {
                v = Integer.parseInt(s.toString());
            } catch (Exception localException) {
            }
        }
        return v;
    }

    public static Integer null2Integer(Object s) {
        Integer v = null;
        if (s != null) {
            try {
                v = Integer.parseInt(s.toString());
            } catch (Exception localException) {
            }
        }
        return v;
    }

    /**将null转为float
     * @param s
     * @return
     */
    public static float null2Float(Object s) {
        float v = 0.0F;
        if (s != null) {
            try {
                v = Float.parseFloat(s.toString());
            } catch (Exception localException) {
            }
        }
        return v;
    }

    /**将null转为double
     * @param s
     * @return
     */
    public static double null2Double(Object s) {
        double v = 0.0D;
        if (s != null) {
            try {
                v = Double.parseDouble(null2String(s));
            } catch (Exception localException) {
            }
        }
        return v;
    }

    /**将null转为boolean
     * @param s
     * @return
     */
    public static boolean null2Boolean(Object s) {
        boolean v = false;
        if (s != null) {
            try {
                v = Boolean.parseBoolean(s.toString());
            } catch (Exception localException) {
            }
        }
        return v;
    }

    /**将null 或“null”转为String空
     * @param s
     * @return
     */
    public static String null2String(Object s) {
        String str = "";
        str = s == null ? str : s.toString().trim();
        if ("null".equals(str))
            str = "";
        return str;
    }

    /**将null转为long
     * @param s
     * @return
     */
    public static Long null2Long(Object s) {
        Long v = Long.valueOf(-1L);
        if (s != null) {
            try {
                v = Long.valueOf(Long.parseLong(s.toString()));
            } catch (Exception localException) {
            }
        }
        return v;
    }

    public static String getTimeInfo(long time) {
        int hour = (int) time / 3600000;
        long balance = time - hour * 1000 * 60 * 60;
        int minute = (int) balance / 60000;
        balance -= minute * 1000 * 60;
        int seconds = (int) balance / 1000;
        String ret = "";
        if (hour > 0) {
            ret = ret + hour + "小时";
        }
        if (minute > 0) {
            ret = ret + minute + "分";
        } else if ((minute <= 0) && (seconds > 0)) {
            ret = ret + "零";
        }
        if (seconds > 0) {
            ret = ret + seconds + "秒";
        }
        return ret;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static int indexOf(String s, String sub) {
        return s.trim().indexOf(sub.trim());
    }

    public static Map<String, Object> cal_time_space(Date begin, Date end) {
        long l = end.getTime() - begin.getTime();
        long day = l / 86400000L;
        long hour = l / 3600000L - day * 24L;
        long min = l / 60000L - day * 24L * 60L - hour * 60L;
        long second = l / 1000L - day * 24L * 60L * 60L - hour * 60L * 60L - min * 60L;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("day", Long.valueOf(day));
        map.put("hour", Long.valueOf(hour));
        map.put("min", Long.valueOf(min));
        map.put("second", Long.valueOf(second));
        return map;
    }

    /**生成n位随机字符串
     * @param length
     * @return
     */
    public static final String randomString(int length) {
        char[] numbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            .toCharArray();
        if (length < 1) {
            return "";
        }
        Random randGen = new Random();
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
        }
        return new String(randBuffer);
    }

    /**生成n位随机数字
     * @param length
     * @return
     */
    public static final String randomInt(int length) {
        if (length < 1) {
            return null;
        }
        Random randGen = new Random();
        char[] numbersAndLetters = "0123456789".toCharArray();

        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(10)];
        }
        return new String(randBuffer);
    }

    public static long getDateDistance(String time1, String time2) {
        long quot = 0L;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = ft.parse(time1);
            Date date2 = ft.parse(time2);
            quot = date1.getTime() - date2.getTime();
            quot = quot / 1000L / 60L / 60L / 24L;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return quot;
    }

    public static double div(Object a, Object b) {
        double ret = 0.0D;
        if ((!null2String(a).equals("")) && (!null2String(b).equals(""))) {
            BigDecimal e = new BigDecimal(null2String(a));
            BigDecimal f = new BigDecimal(null2String(b));
            if (null2Double(f) > 0.0D) {
                ret = e.divide(f, 3, 1).doubleValue();
            }
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.valueOf(df.format(ret)).doubleValue();
    }

    public static double subtract(Object a, Object b) {
        double ret = 0.0D;
        BigDecimal e = new BigDecimal(null2Double(a));
        BigDecimal f = new BigDecimal(null2Double(b));
        ret = e.subtract(f).doubleValue();
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.valueOf(df.format(ret)).doubleValue();
    }

    public static double add(Object a, Object b) {
        double ret = 0.0D;
        BigDecimal e = new BigDecimal(null2Double(a));
        BigDecimal f = new BigDecimal(null2Double(b));
        ret = e.add(f).doubleValue();
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.valueOf(df.format(ret)).doubleValue();
    }

    public static double mul(Object a, Object b) {
        BigDecimal e = new BigDecimal(null2Double(a));
        BigDecimal f = new BigDecimal(null2Double(b));
        double ret = e.multiply(f).doubleValue();
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.valueOf(df.format(ret)).doubleValue();
    }

    public static double formatMoney(Object money) {
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.valueOf(df.format(money)).doubleValue();
    }

    public static int M2byte(float m) {
        float a = m * 1024.0F * 1024.0F;
        return (int) a;
    }

    public static boolean convertIntToBoolean(int intValue) {
        return intValue != 0;
    }

    public static String getURL(HttpServletRequest request) {
        String contextPath = request.getContextPath().equals("/") ? "" : request.getContextPath();
        String url = "http://" + request.getServerName();
        if (null2Int(Integer.valueOf(request.getServerPort())) != 80) {
            url = url + ":" + null2Int(Integer.valueOf(request.getServerPort())) + contextPath;
        } else {
            url = url + contextPath;
        }
        return url;
    }

    public static int parseDate(String type, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (type.equals("y")) {
            return cal.get(1);
        }
        if (type.equals("M")) {
            return cal.get(2) + 1;
        }
        if (type.equals("d")) {
            return cal.get(5);
        }
        if (type.equals("H")) {
            return cal.get(11);
        }
        if (type.equals("m")) {
            return cal.get(12);
        }
        if (type.equals("s")) {
            return cal.get(13);
        }
        return 0;
    }

    public static boolean fileExist(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static int splitLength(String s, String c) {
        int v = 0;
        if (!s.trim().equals("")) {
            v = s.split(c).length;
        }
        return v;
    }

    static int totalFolder = 0;
    static int totalFile   = 0;

    /**统计某文件夹及子目录里的文件数量
     * @param folder
     * @return
     */
    public static double fileSize(File folder) {
        totalFolder += 1;

        long foldersize = 0L;
        File[] filelist = folder.listFiles();
        if (filelist == null)
            return 0d;
        for (int i = 0; i < filelist.length; i++) {
            if (filelist[i].isDirectory()) {
                foldersize = (long) (foldersize + fileSize(filelist[i]));
            } else {
                totalFile += 1;
                foldersize += filelist[i].length();
            }
        }
        return div(Long.valueOf(foldersize), Integer.valueOf(1024));
    }

    /**统计某路径下文件的数量
     * @param file
     * @return
     */
    public static int fileCount(File file) {
        if (file == null) {
            return 0;
        }
        if (!file.isDirectory()) {
            return 1;
        }
        int fileCount = 0;
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                fileCount++;
            } else if (f.isDirectory()) {
                fileCount++;
                fileCount += fileCount(file);
            }
        }
        return fileCount;
    }

    public static String get_all_url(HttpServletRequest request) {
        String query_url = request.getRequestURI();
        if ((request.getQueryString() != null) && (!request.getQueryString().equals(""))) {
            query_url = query_url + "?" + request.getQueryString();
        }
        return query_url;
    }

    public static Color getColor(String color) {
        if (color.charAt(0) == '#') {
            color = color.substring(1);
        }
        if (color.length() != 6) {
            return null;
        }
        try {
            int r = Integer.parseInt(color.substring(0, 2), 16);
            int g = Integer.parseInt(color.substring(2, 4), 16);
            int b = Integer.parseInt(color.substring(4), 16);
            return new Color(r, g, b);
        } catch (NumberFormatException nfe) {
        }
        return null;
    }

    /**生成n位不重复的数字
     * @param a
     * @param length
     * @return
     */
    public static Set<Integer> randomInt(int a, int length) {
        Set<Integer> list = new TreeSet<Integer>();
        int size = length;
        if (length > a) {
            size = a;
        }
        while (list.size() < size) {
            Random random = new Random();
            int b = random.nextInt(a);
            list.add(Integer.valueOf(b));
        }
        return list;
    }

    public static Double formatDouble(Object obj, int len) {
        String format = "0.0";
        for (int i = 1; i < len; i++) {
            format = format + "0";
        }
        DecimalFormat df = new DecimalFormat(format);
        return Double.valueOf(df.format(obj));
    }

    /**判断字符是不是中文
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if ((ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)
            || (ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS)
            || (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A)
            || (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION)
            || (ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION)
            || (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)) {
            return true;
        }
        return false;
    }

    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0.0F;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count += 1.0F;
                    System.out.print(c);
                }
            }
        }
        float result = count / chLength;
        if (result > 0.4D) {
            return true;
        }
        return false;
    }

    public static String trimSpaces(String IP) {
        while (IP.startsWith(" ")) {
            IP = IP.substring(1, IP.length()).trim();
        }
        while (IP.endsWith(" ")) {
            IP = IP.substring(0, IP.length() - 1).trim();
        }
        return IP;
    }

    /**验证ip地址
     * @param IP
     * @return
     */
    public static boolean isIp(String IP) {
        boolean b = false;
        IP = trimSpaces(IP);
        if (IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            String[] s = IP.split("\\.");
            if ((Integer.parseInt(s[0]) < 255) && (Integer.parseInt(s[1]) < 255)
                && (Integer.parseInt(s[2]) < 255) && (Integer.parseInt(s[3]) < 255)) {
                b = true;
            }
        }
        return b;
    }

    public static String generic_domain(HttpServletRequest request) {
        String system_domain = "localhost";
        String serverName = request.getServerName();
        if (isIp(serverName)) {
            system_domain = serverName;
        } else {
            system_domain = serverName.substring(serverName.indexOf(".") + 1);
        }
        return system_domain;
    }

    public static String getthekey(String l, int n) {
        if (!"".equals(l) && null != l) {
            return l.split(",")[n];
        }
        return "";

    }

    /**获取项目路径
     * @param request
     * @return
     */
    public static String getContentPath(HttpServletRequest request, String url) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                          + request.getServerPort() + path + "/";
        return basePath + null2String(url);
    }

    /**
     * 获取项目根路径
     * @return
     */
    public static String getRootPath() {
        String classPath = CommUtil.class.getResource("/").getPath();
        String rootPath = "";
        //windows下
        if ("\\".equals(File.separator)) {
            rootPath = classPath.substring(1, classPath.indexOf("/WEB-INF/classes"));
            rootPath = rootPath.replace("/", "\\");
        }
        //linux下
        if ("/".equals(File.separator)) {
            rootPath = classPath.substring(0, classPath.indexOf("/WEB-INF/classes"));
            rootPath = rootPath.replace("\\", "/");
        }
        return rootPath;
    }

    /**截取字符串   从第1个截取到-1个    去头和尾
     * @param typesId
     * @return
     */
    public static String subString1Tolen1(String typesId) {
        return typesId.substring(1, typesId.length() - 1);
    }

    public static String getCurrentTime() {
        return getCurrentTime("yyyy-MM-dd HH:mm:ss");
    }

    public static String getCurrentTime(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date(System.currentTimeMillis()));
    }

    public static ResultMsg setResultMsgData(ResultMsg rmg, boolean result, String msg) {
        if (rmg == null) {
            rmg = new ResultMsg();
        }
        rmg.setResult(result);
        rmg.setMsg(msg);
        return rmg;
    }

    public static String parseDateTime(Date date) {
        return parseDateTime("yyyy-MM-dd HH:mm:ss", date);
    }

    public static String parseShortDateTime(Date date) {
        return parseDateTime("yyyy-MM-dd", date);
    }

    public static String parseDateTimeHourMinute(Date date) {
        return parseDateTime("HH:mm", date);
    }

    public static String parseDateTime(String type, Date date) {
        if (date == null) {
            return "";
        }
        return formatDate(type, date);
    }

    /**
     * 格式化时间显示
     * 
     * @param date
     * @return
     */
    public static String formatDate(String type, Date date) {
        return new SimpleDateFormat(type).format(date);
    }

    public static ModelAndView successPage(String url, String op_title, HttpServletRequest request,
                                           HttpServletResponse response) {
        ModelAndView mv = new JModelAndView("common/success.html", request, response);
        mv.addObject("url", url);
        mv.addObject("op_title", op_title);
        return mv;
    }

    public static ModelAndView errorPage(String url, String op_title, HttpServletRequest request,
                                         HttpServletResponse response) {
        ModelAndView mv = new JModelAndView("common/error.html", request, response);
        mv.addObject("url", url);
        mv.addObject("op_title", op_title);
        return mv;
    }

    public static ModelAndView fullErrorPage(String url, String op_title,
                                             HttpServletRequest request,
                                             HttpServletResponse response) {
        ModelAndView mv = new JModelAndView("common/full_screen_error.html", request, response);
        mv.addObject("url", url);
        mv.addObject("op_title", op_title);
        return mv;
    }

    public static String encodeExportFileName(HttpServletRequest request, String fileName) {
        try {
            String userAgent = request.getHeader("USER-AGENT");
            if (userAgent.contains("MSIE") || userAgent.contains("Trident")
                || userAgent.contains("Edge")) {
                fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");//非IE浏览器的处理：
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public static String formatFileSize(String fileSize) {
        float size = null2Float(fileSize);
        long bytes = (long) size;
        String fileSizeString = "";
        DecimalFormat df = new DecimalFormat("#.00");
        if (bytes < 1024) {
            fileSizeString = df.format((double) bytes) + "B";
        } else if (bytes < 1048576) {
            fileSizeString = df.format((double) bytes / 1024) + "KB";
        } else if (bytes < 1073741824) {
            fileSizeString = df.format((double) bytes / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) bytes / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public static boolean isImage(String mimetype) {
        if (CommUtil.isBlank(mimetype)) {
            return false;
        }
        String type = mimetype.split("/")[0];
        return type.equals("image");
    }

    public static boolean isOffice(String ext_name) {
        if (CommUtil.isBlank(ext_name)) {
            return false;
        }
        if (".doc".equalsIgnoreCase(ext_name) || ".docx".equalsIgnoreCase(ext_name)
            || ".xls".equalsIgnoreCase(ext_name) || ".xlsx".equalsIgnoreCase(ext_name)
            || ".ppt".equalsIgnoreCase(ext_name) || ".pptx".equalsIgnoreCase(ext_name)) {
            return true;
        }
        return false;
    }

    public static boolean isPDF(String ext_name) {
        if (CommUtil.isBlank(ext_name)) {
            return false;
        }
        return ".pdf".equalsIgnoreCase(ext_name);
    }

    //校验手机号
    public static boolean matcherMobile(String mobile) {
        if (CommUtil.isBlank(mobile)) {
            return false;
        }
        if (mobile.trim().length() != 11) {
            return false;
        }
        Pattern pattern = Pattern
            .compile("^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

    public static Integer getAge(Date birthDay) {
        if (birthDay == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            return -1;//错误的年龄
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age;
    }

    public static String getFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        return getDay(-(cal.get(Calendar.DAY_OF_MONTH) - 1));
    }

    public static String getLastDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        return getDay(cal.getActualMaximum(Calendar.DAY_OF_MONTH) - cal.get(Calendar.DAY_OF_MONTH));
    }

    public static String getDay(int add) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH) + add;
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append(year).append("-");
        if (month < 10) {
            sBuffer.append("0").append(month).append("-");
        } else {
            sBuffer.append(month).append("-");
        }
        if (day < 10) {
            sBuffer.append("0").append(day);
        } else {
            sBuffer.append(day);
        }
        return sBuffer.toString();
    }

    public static int getThisYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    public static int getYear(String dateStr, String format) {
        if (isBlank(dateStr)) {
            return 0;
        }
        Date date = formatDate(dateStr, format);
        return getYear(date);
    }

    public static int getYear(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static int getMonth(String dateStr, String format) {
        if (isBlank(dateStr)) {
            return 0;
        }
        Date date = formatDate(dateStr, format);
        return getMonth(date);
    }

    public static int getMonth(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    //获取某月天数
    public static int getDaysOfMonth(String dateStr) {
        return getDaysOfMonth(dateStr, "yyyy-MM-dd");
    }

    //获取某月天数
    public static int getDaysOfMonth(String dateStr, String format) {
        if (isBlank(dateStr)) {
            return 0;
        }
        Date date = formatDate(dateStr, format);
        return getDaysOfMonth(date);
    }

    //获取某月天数
    public static int getDaysOfMonth(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //获取某天到月末的天数
    public static int getDaysOfMonthFromDate(String dateStr) {
        return getDaysOfMonthFromDate(dateStr, "yyyy-MM-dd");
    }

    //获取某天到月末的天数
    public static int getDaysOfMonthFromDate(String dateStr, String format) {
        if (isBlank(dateStr)) {
            return 0;
        }
        Date date = formatDate(dateStr, format);
        return getDaysOfMonthFromDate(date);
    }

    //获取某天到月末的天数
    public static int getDaysOfMonthFromDate(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH) - cal.get(Calendar.DAY_OF_MONTH);
    }
}
