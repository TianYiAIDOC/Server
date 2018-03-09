package com.tianyi.dao;


import com.tianyi.bo.DataRegion;
import com.tianyi.dao.base.BaseDao;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 雪峰 on 2018/2/7.
 */
@Repository
public class DataRegionDao extends BaseDao<DataRegion> {

    /**
     * 国家简码列表
     */
    public List<DataRegion> getDataRegions(final int pid ,final int page, final int pageSize) {
        StringBuffer strb = new StringBuffer("SELECT * FROM data_region WHERE pid= ").append(pid);
        strb.append(" ORDER BY id desc ");

        List<DataRegion> results = execute(session -> session
                .createNativeQuery(strb.toString(), DataRegion.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList());
        return results != null ? results : new ArrayList<DataRegion>();
    }



    /**
     *加载世界国家
     */
    public List<DataRegion> getCountrys(final int page, final int pageSize) {
        StringBuffer strb = new StringBuffer("SELECT * FROM data_region WHERE level= 2");
        strb.append(" ORDER BY id asc ");

        List<DataRegion> results = execute(session -> session
                .createNativeQuery(strb.toString(), DataRegion.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList());
        return results != null ? results : new ArrayList<DataRegion>();
    }


    public Integer getDataRegionsTotal(final int pid ) {
        StringBuffer strb = new StringBuffer("SELECT count(*)  FROM data_region WHERE pid= ").append(pid);
        BigInteger num = execute(session -> (BigInteger) session
                .createNativeQuery(strb.toString())
                .getSingleResult());
        return num == null ? 0 : num.intValue();
    }
    public Integer getCountryTotal() {
        StringBuffer strb = new StringBuffer("SELECT count(*)  FROM data_region WHERE level= 2");
        BigInteger num = execute(session -> (BigInteger) session
                .createNativeQuery(strb.toString())
                .getSingleResult());
        return num == null ? 0 : num.intValue();
    }

}
