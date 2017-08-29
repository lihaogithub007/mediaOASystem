package com.yuyu.soft.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IBaseDao<T> {

    /**
     * 保存
     * @param o
     * @return
     */
    public Serializable save(T o);

    /**批量保存
     * @param t
     */
    public void saveCollection(List<T> t);

    /**
     * 删除
     * @param o
     */
    public void delete(T o);

    /**
     * 批量删除
     */
    public void deleteCollection(List<T> t);

    /**
     * 更新
     * @param o
     */
    public void update(T o);

    /**批量更新
     * @param os
     */
    public void updateCollection(List<T> o);

    /**
     * 保存并更新
     * @param o
     */
    public void saveOrUpdate(T o);

    /**
     * 根据ID获取对象信息
     * @param c
     * @param id
     * @return
     */
    public T get(Class<T> c, Serializable id);

    /**
     * 根据HQL获取对象信息
     * @param hql
     * @return
     */
    public T get(String hql);

    /**
     * 根据HQL获取对象信息
     * @param hql
     * @param params
     * @return
     */
    public T get(String hql, Map<String, Object> params);

    public List<T> find(String hql);

    public List<Map<String, Object>> findMap(String hql, Map<String, Object> paramsMap);

    public List<T> find(String hql, Map<String, Object> params);

    public List<T> find(String hql, int start, int rows);

    public List<T> find(String hql, Map<String, Object> params, int start, int rows);

    public int count(String hql);

    public int count(String hql, Map<String, Object> params);

    public int executeHql(String hql);

    public List<T> findBySql(String hql, Map<String, Object> params, Class<T> po);

    public List<T> findBySql(String hql, Map<String, Object> params, int start, int rows,
                             Class<T> po);

    public List<Object[]> findBySql(String hql, Map<String, Object> params, int start, int rows);

    public List executeNativeQuery(String hql, Map<String, Object> params, int start, int rows);

    public int countBySql(String hql, Map<String, Object> params);

    List<Map<String, Object>> findListMapBySQL(String sql, Map<String, Object> params, int start,
                                               int rows);

}
