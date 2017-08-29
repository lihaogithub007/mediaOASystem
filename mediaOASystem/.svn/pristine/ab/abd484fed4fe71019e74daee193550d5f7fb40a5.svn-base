package com.yuyu.soft.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Elise on 2016/7/1.
 */
public class PropertyUtils {

    private static Properties properties = new Properties();

    public static Object getValue(String path, String key) {

        InputStream stream = PropertyUtils.class.getClassLoader().getResourceAsStream(path);
        try {
            properties.load(stream);
            stream.close();
        } catch (Exception e) {
            try {
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return properties.get(key);
    }

    public static Properties getProperty(String path) {
        Properties prop = new Properties();
        InputStream stream = PropertyUtils.class.getClassLoader().getResourceAsStream(path);
        try {
            prop.load(stream);
            stream.close();
        } catch (Exception e) {
            try {
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return prop;
    }

    public static void main(String[] arg) {

        Properties prop = PropertyUtils.getProperty("sms.properties");
        Iterator it = prop.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key + "==>" + value);
        }
    }
}
