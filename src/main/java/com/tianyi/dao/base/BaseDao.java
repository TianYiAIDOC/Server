package com.tianyi.dao.base;

import com.tianyi.bo.base.BaseBo;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

/**
 * Created by 雪峰 on 2018/1/1.
 */

@Transactional
public class BaseDao<T extends BaseBo> extends HibernateDaoSupport {
    private final Class<T> clazz;
    @Resource(name = "cacheManager")
    private CacheManager cacheManager;

    @Resource(name = "sessionFactory")
    public void setBaseSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public BaseDao() {
        clazz = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected <E> E execute(HibernateCallback<E> action) {
        return getHibernateTemplate().execute(action);
    }
    

    public T getById(final Serializable id) {
        String key = clazz.getCanonicalName() + id;
        Cache.ValueWrapper cachedObject = cacheManager.getCache("default").get(key);
        if (cachedObject != null) {
            return (T) cachedObject.get();
        }

        T dbObject = getHibernateTemplate().get(clazz, id);
        cacheManager.getCache("default").put(key, dbObject);
        return dbObject;
    }

    @Transactional(readOnly = false)
    public void update(final T t) {
        Date now = new Date();
        t.setUpdatedOn(now);
        t.setUpdatedTimestamp(now.getTime());
        getHibernateTemplate().update(t);

        String key = clazz.getCanonicalName() + t.getId();
        cacheManager.getCache("default").evict(key);
    }

    @Transactional(readOnly = false)
    public void delete(T t) {
        getHibernateTemplate().delete(t);
        String key = clazz.getCanonicalName() + t.getId();
        cacheManager.getCache("default").evict(key);
    }

    @Transactional(readOnly = false)
    public Serializable add(T t) {
        Date now = new Date();
        t.setCreatedOn(now);
        t.setUpdatedOn(now);
        t.setUpdatedTimestamp(now.getTime());
        Serializable id = getHibernateTemplate().save(t);
        return id;
    }

    @Transactional(readOnly = false)
    public <T> List<T> getPageList(Class<T> entityClass, final int start, final int limit, String params) {
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("where 1=1 ");
        if (!params.equals("")) {
            sBuffer.append("and " + params);
        }
        sBuffer.append(" order by id DESC");
        String entityName = entityClass.getName();
        final String sqlStr = "from " + entityName + " t " + sBuffer.toString();
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(sqlStr);
                query.setFirstResult(start * limit);
                query.setMaxResults(limit);
                return query.list();
            }
        });
    }

    @Transactional(readOnly = false)
    public Integer getTotalCount(Class<T> entityClass, String params) {
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("where 1=1 ");
        if (!params.equals("")) {
            sBuffer.append("and " + params);
        }
        sBuffer.append(" order by id DESC");
        final String hql = "select count(*) from " + entityClass.getName() + " t " + sBuffer.toString();

        Long num = execute(new HibernateCallback<Long>() {
            @Override
            public Long doInHibernate(Session session) throws HibernateException {
                return (Long) session
                        .createQuery(hql)
                        .uniqueResult();
            }
        });
        return num == null ? 0 : num.intValue();
    }


}
