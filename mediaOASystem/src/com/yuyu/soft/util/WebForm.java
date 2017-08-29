package com.yuyu.soft.util;

import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.easyjf.beans.BeanUtils;
import com.easyjf.beans.BeanWrapper;
import com.yuyu.soft.base.annotation.FormIgnore;

public class WebForm {

    public void Map2Obj(List<Map> maps, Object obj) {
        BeanWrapper wrapper = new BeanWrapper(obj);
        PropertyDescriptor[] propertys = wrapper.getPropertyDescriptors();
        for (int i = 0; i < propertys.length; i++) {
            String name = propertys[i].getName();
            if ((wrapper.isWritableProperty(name)) && (propertys[i].getWriteMethod() != null)) {
                Object propertyValue = null;
                for (int j = 0; j < maps.size(); j++) {
                    Map map = (Map) maps.get(j);
                    Iterator keys = map.keySet().iterator();
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        if (key.equals(propertys[i].getName())) {
                            FormIgnore fi = null;
                            fi = (FormIgnore) propertys[i].getWriteMethod().getAnnotation(
                                FormIgnore.class);
                            if (fi == null) {
                                try {
                                    Field f = propertys[i].getWriteMethod().getDeclaringClass()
                                        .getDeclaredField(name);
                                    fi = (FormIgnore) f.getAnnotation(FormIgnore.class);
                                } catch (SecurityException e) {
                                    e.printStackTrace();
                                } catch (NoSuchFieldException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (fi == null) {
                                try {
                                    propertyValue = BeanUtils.convertType(map.get(key),
                                        propertys[i].getPropertyType());
                                } catch (Exception e) {
                                    if (propertys[i].getPropertyType().toString().equals("int")) {
                                        propertyValue = Integer.valueOf(0);
                                    }
                                    if (propertys[i].getPropertyType().toString().toLowerCase()
                                        .indexOf("boolean") >= 0) {
                                        propertyValue = Boolean.valueOf(false);
                                    }
                                    if (propertys[i].getPropertyType().toString().toLowerCase()
                                        .indexOf("class java.sql.timestamp") >= 0) {
                                        propertyValue = java.sql.Timestamp.valueOf((String) map
                                            .get(key));
                                    }
                                }
                                wrapper.setPropertyValue(propertys[i].getName(), propertyValue);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 用于填充form表单到实体对象
     * @param <T>
     * @param request
     * @param classType
     * @return
     */
    public <T> T toPo(HttpServletRequest request, Class<T> classType) {
        T obj = null;
        try {
            obj = classType.newInstance();
            Map map = request.getParameterMap();
            Enumeration enum1 = request.getParameterNames();
            List<Map> maps = new ArrayList();
            while (enum1.hasMoreElements()) {
                String paramName = (String) enum1.nextElement();
                String value = request.getParameter(paramName);
                Map m1 = new HashMap();
                m1.put(paramName, value);
                maps.add(m1);
            }
            Map2Obj(maps, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 用于填充form表单到实体对象
     * @param request
     * @param obj
     * @return
     */
    public Object toPo(HttpServletRequest request, Object obj) {
        try {
            Map map = request.getParameterMap();
            Enumeration enum1 = request.getParameterNames();
            List<Map> maps = new ArrayList();
            while (enum1.hasMoreElements()) {
                String paramName = (String) enum1.nextElement();
                String value = request.getParameter(paramName);
                Map m1 = new HashMap();
                m1.put(paramName, value);
                maps.add(m1);
            }
            Map2Obj(maps, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public <T> T jsonToPo(HttpServletRequest request, Class<T> classType) {
        T obj = null;
        String acceptjson = "";
        try {
            obj = classType.newInstance();
            List<Map> maps = new ArrayList();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                (ServletInputStream) request.getInputStream(), "utf-8"));
            StringBuffer sb = new StringBuffer("");
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            acceptjson = sb.toString();
            JSONObject object = JSONObject.fromObject(acceptjson);
            Iterator it = object.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = object.getString(key);
                Map map = new HashMap(1);
                map.put(key, value);
                maps.add(map);
            }
            Map2Obj(maps, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
