package com.yuyu.soft.util;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

public class CopyUtil {  
	  
    /**   */  
    /** 
     * Copy properties of orig to dest Exception the Entity and Collection Type 
     *  
     * @param dest 
     * @param orig 
     * @return the dest bean 
     */  
    public static Object poToVo(Object dest, Object orig) {  
        if (dest == null || orig == null) {  
            return dest;  
        }  
  
        PropertyDescriptor[] destDesc = PropertyUtils.getPropertyDescriptors(dest);  
        try {  
            for (int i = 0; i < destDesc.length; i++) {  
                Class destType = destDesc[i].getPropertyType();  
                Class origType = PropertyUtils.getPropertyType(orig, destDesc[i].getName());  
                if (destType != null && destType.equals(origType) && !destType.equals(Class.class)) {  
                    if (!Collection.class.isAssignableFrom(origType)) {  
                        try {  
                            Object value = PropertyUtils.getProperty(orig, destDesc[i].getName());  
                            PropertyUtils.setProperty(dest, destDesc[i].getName(), value);  
                        } catch (Exception ex) {}  
                    }  
                }  
            }  
  
            return dest;  
        } catch (Exception ex) {  
            ex.printStackTrace();
            // return dest;
            return null;
        }  
    }  
  
    /**   */  
    /** 
     * Copy properties of orig to dest Exception the Entity and Collection Type 
     *  
     * @param dest 
     * @param orig 
     * @param ignores 
     * @return the dest bean 
     */  
    public static Object copyProperties(Object dest, Object orig, String[] ignores) {  
        if (dest == null || orig == null) {  
            return dest;  
        }  
  
        PropertyDescriptor[] destDesc = PropertyUtils.getPropertyDescriptors(dest);  
        try {  
            for (int i = 0; i < destDesc.length; i++) {  
                if (contains(ignores, destDesc[i].getName())) {  
                    continue;  
                }  
  
                Class destType = destDesc[i].getPropertyType();  
                Class origType = PropertyUtils.getPropertyType(orig, destDesc[i].getName());  
                if (destType != null && destType.equals(origType) && !destType.equals(Class.class)) {  
                    if (!Collection.class.isAssignableFrom(origType)) {  
                        Object value = PropertyUtils.getProperty(orig, destDesc[i].getName());  
                        PropertyUtils.setProperty(dest, destDesc[i].getName(), value);  
                    }  
                }  
            }  
  
            return dest;  
        } catch (Exception ex) {  
            ex.printStackTrace();
            return null;
        }  
    }  
  
    static boolean contains(String[] ignores, String name) {  
        boolean ignored = false;  
        for (int j = 0; ignores != null && j < ignores.length; j++) {  
            if (ignores[j].equals(name)) {  
                ignored = true;  
                break;  
            }  
        }  
  
        return ignored;  
    }  
    
    
    
    /**
     * 将一个 Map 对象转化为一个 JavaBean
     * @param type 要转化的类型
     * @param map 包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException
     *             如果分析类属性失败
     * @throws IllegalAccessException
     *             如果实例化 JavaBean 失败
     * @throws InstantiationException
     *             如果实例化 JavaBean 失败
     * @throws InvocationTargetException
     *             如果调用属性的 setter 方法失败
     */
    public static Object mapToVo(Class type, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象

        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);

                Object[] args = new Object[1];
                args[0] = value;

                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }

    /**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static Map convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }
} 
