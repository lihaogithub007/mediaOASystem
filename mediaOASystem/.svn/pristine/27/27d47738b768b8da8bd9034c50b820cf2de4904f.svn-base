package com.yuyu.soft.util.sms;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.HttpUtil;
import com.yuyu.soft.util.Md5;
import com.yuyu.soft.util.PropertyUtils;

/**
 * 美联软通短信接口
 */
public class MeilianSmsSender {

    protected static final Log log = LogFactory.getLog(MeilianSmsSender.class);

    private static String      sms_url;

    private static String      sms_secret;

    private static String      sms_username;

    private static String      sms_password;

    public static boolean sendMsg(String mobile, String content) throws Exception {

        boolean flag = false;

        Properties prop = PropertyUtils.getProperty("sms.properties");
        sms_url = (String) prop.get("sms_url");
        sms_secret = (String) prop.get("sms_secret");
        sms_username = (String) prop.get("sms_username");
        sms_password = (String) prop.get("sms_password");

        boolean smsDebug = CommUtil.null2Boolean(prop.get("sms_debug"));

        Map<String, String> map = new HashMap<String, String>();

        content = "【新媒体新闻编辑部】" + content;

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        String result = "";
        content = URLEncoder.encode(content, "UTF-8");
        map.put("username", sms_username);
        map.put("password_md5", Md5.getMd5String(sms_password).toLowerCase());
        map.put("mobile", mobile);
        map.put("apikey", sms_secret);
        map.put("content", content);
        map.put("encode", "UTF-8");
        if (!smsDebug) {
            result = HttpUtil.post(sms_url, map, headers);
        } else {
            //调试模式
            result = "success";
        }
        if (result.contains("success")) {
            flag = true;
            log.info("短信发送成功");
        } else {
            log.error("短信发送失败,短信接口返回内容：" + result);
        }
        return flag;
    }
}
