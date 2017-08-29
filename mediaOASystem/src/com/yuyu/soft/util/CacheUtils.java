package com.yuyu.soft.util;

import java.io.File;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CacheUtils {

    private static CacheManager cacheManager;

    static {
        String rootPath = CommUtil.getRootPath();
        String path = rootPath + File.separator + "WEB-INF" + File.separator + "classes"
                      + File.separator + "ehcache.xml";
        cacheManager = new CacheManager(path);
    }

    /**
     * 获取缓存
     */
    public static Object get(String cacheName, String key) {
        Element element = getCache(cacheName).get(key);
        return element == null ? null : element.getObjectValue();
    }

    /**
     * 写入缓存
     */
    public static void put(String cacheName, String key, Object value) {
        Element element = new Element(key, value);
        getCache(cacheName).put(element);
    }

    /**
     * 从缓存中移除
     */
    public static void remove(String cacheName, String key) {
        getCache(cacheName).remove(key);
    }

    /**
     * 获得一个Cache，没有则创建一个。
     * @param cacheName
     * @return
     */
    private static Cache getCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            cacheManager.addCache(cacheName);
            cache = cacheManager.getCache(cacheName);
            cache.getCacheConfiguration().setEternal(true);
        }
        return cache;
    }

    public static CacheManager getCacheManager() {
        return cacheManager;
    }

}
