package com.yuyu.soft.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.yuyu.soft.dao.IBaseDao;

@Repository
public class BaseDaoImpl<T> implements IBaseDao<T> {

    @Resource
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public Serializable save(T o) {
        return this.getCurrentSession().save(o);
    }

    public void saveCollection(List<T> os) {
        for (T t : os) {
            this.getCurrentSession().save(t);
        }
    }

    public T get(Class<T> c, Serializable id) {
        return (T) this.getCurrentSession().get(c, id);
    }

    public T get(String hql) {
        Query q = this.getCurrentSession().createQuery(hql);
        List<T> l = q.list();
        if (l != null && l.size() > 0) {
            return l.get(0);
        }
        return null;
    }

    public T get(String hql, Map<String, Object> params) {
        Query q = this.getCurrentSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        List<T> l = q.list();
        if (l != null && l.size() > 0) {
            return l.get(0);
        }
        return null;
    }

    public void delete(T o) {
        this.getCurrentSession().delete(o);
    }

    public void deleteCollection(List<T> t) {
        if (t != null && t.size() > 0) {
            for (T t2 : t) {
                this.getCurrentSession().delete(t2);
            }
        }
    }

    public void update(T o) {
        this.getCurrentSession().update(o);
    }

    /**批量更新
     * @param os
     */
    public void updateCollection(List<T> o) {
        if (o != null && o.size() > 0) {
            for (T t : o) {
                this.getCurrentSession().update(t);
            }
        }
    }

    public void saveOrUpdate(T o) {
        this.getCurrentSession().saveOrUpdate(o);
    }

    public List<T> find(String hql) {
        Query q = this.getCurrentSession().createQuery(hql);
        return q.list();
    }

    public List<Map<String, Object>> findMap(String hql, Map<String, Object> paramsMap) {
        Query q = this.getCurrentSession().createQuery(hql);
        if (paramsMap != null && !paramsMap.isEmpty()) {
            for (String key : paramsMap.keySet()) {
                q.setParameter(key, paramsMap.get(key));
            }
        }
        return q.list();
    }

    public List<T> find(String hql, Map<String, Object> params) {
        Query q = this.getCurrentSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.list();
    }

    public List<T> find(String hql, Map<String, Object> params, int start, int rows) {
        Query q = this.getCurrentSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.setFirstResult(start).setMaxResults(rows).list();
    }

    public List<T> find(String hql, int start, int rows) {
        Query q = this.getCurrentSession().createQuery(hql);
        return q.setFirstResult(start).setMaxResults(rows).list();
    }

    public int count(String hql) {
        Query q = this.getCurrentSession().createQuery(hql);
        return Integer.parseInt(q.uniqueResult().toString());
    }

    public int count(String hql, Map<String, Object> params) {
        Query q = this.getCurrentSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return Integer.parseInt(q.uniqueResult().toString());
    }

    public int executeHql(String hql) {
        Query q = this.getCurrentSession().createQuery(hql);
        return q.executeUpdate();
    }

    @Override
    public List<T> findBySql(String hql, Map<String, Object> params, Class<T> po) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        q.setResultTransformer(Transformers.aliasToBean(po));
        return q.list();
    }

    @Override
    public List<T> findBySql(String hql, Map<String, Object> params, int start, int rows,
                             Class<T> po) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }

        q.setResultTransformer(Transformers.aliasToBean(po));
        return q.setFirstResult(start).setMaxResults(rows).list();
    }

    @Override
    public List<Object[]> findBySql(String hql, Map<String, Object> params, int start, int rows) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.setFirstResult(start).setMaxResults(rows).list();
    }

    @Override
    public List executeNativeQuery(String hql, Map<String, Object> params, int start, int rows) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.setFirstResult(start).setMaxResults(rows).list();
    }

    @Override
    public List<Map<String, Object>> findListMapBySQL(String sql, Map<String, Object> params,
                                                      int start, int rows) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return q.setFirstResult(start).setMaxResults(rows).list();
    }

    @Override
    public int countBySql(String hql, Map<String, Object> params) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return Integer.parseInt(q.uniqueResult().toString());
    }

}
