package com.tianyi.dao;

import com.tianyi.bo.Area;
import com.tianyi.dao.base.BaseDao;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Repository
public class AreaDao extends BaseDao<Area> {
    /**
     * 获得所有区域名称
     * 全部省parentId：0
     */
    public List<Area> getAreas(final int parentId) {
        List<Area> results = execute(session -> {
            Query<Area> query = session.createNativeQuery("SELECT * FROM area WHERE parent_id  =:parent_id ", Area.class);
            query.setParameter("parent_id", parentId);
            return query.getResultList();
        });
        return results != null ? results : new ArrayList<Area>();
    }
    /**
     * 获得是否有效的地区
     */
    public List<Area> getAreas(final int parentId, final boolean isUes) {

        List<Area> results = execute(session -> {
            Query<Area> query = session.createNativeQuery("SELECT * FROM area WHERE parent_id=:parent_id  and is_use  =:is_use", Area.class);
            query.setParameter("parent_id", parentId);
            query.setParameter("is_use", isUes);
            return query.getResultList();
        });
        return results != null ? results : new ArrayList<Area>();
    }


    /**
     * 根据areaid查找area
     */
    public Area getAreaByAreaId(final int areaId) {
        return execute(session -> session
                .createNativeQuery("SELECT * FROM area WHERE area_id=:area_id ", Area.class)
                .setParameter("area_id", areaId)
                .uniqueResult());

    }

    /**
     * 获得所有区域名称
     *
     */
    public List<Area> getAreaAll() {
        return execute(session -> {
            Query<Area> query = session.createNativeQuery("SELECT * FROM area ", Area.class);
            return query.getResultList();
        });
    }

    /**
     * 获得是否有效的地区
     * 根据开通情况获取数据列表
     */
    public List<Area> getAreasOpenedAll(final boolean isUes) {
        List<Area> results = execute(session -> {
            Query<Area> query = session.createNativeQuery("SELECT * FROM area WHERE  is_use  =:is_use", Area.class);
            query.setParameter("is_use", isUes);
            return query.getResultList();
        });
        return results != null ? results : new ArrayList<Area>();
    }

    /**
     * 关闭所有地区
     *
     */
    public void updateAreaAllClose() {
        execute(session -> {
            Query<Area> query = session.createNativeQuery("update area set is_use = false", Area.class);
            return query.executeUpdate();
        });
    }

}
